package com.flashsale.service.impl;

import com.flashsale.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import com.flashsale.entity.OrderMsg;
import com.flashsale.mapper.OrderMsgMapper;
import org.springframework.transaction.annotation.Transactional;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {
    private final StringRedisTemplate redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String STOCK_KEY = "seckill:stock:";
    private static final String BLOOM_KEY = "seckill:bloom";
    private static final String ORDER_TOPIC = "seckill-order";
    private static final String IDEMPOTENT_KEY = "seckill:idempotent:";
    private final OrderMsgMapper orderMsgMapper;
    private final RedissonClient redissonClient;

    @Override
    @Transactional
    public String seckill(Long userId, Long skuId) {
        String lockKey = "seckill:lock:" + skuId;
        RLock lock = redissonClient.getLock(lockKey);
        boolean locked = false;
        try {
            locked = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (!locked) {
                return "系统繁忙，请稍后再试";
            }
            String idempotentKey = IDEMPOTENT_KEY + userId + ":" + skuId;
            Boolean success = redisTemplate.opsForValue().setIfAbsent(idempotentKey, "1", 5, TimeUnit.MINUTES);
            if (Boolean.FALSE.equals(success)) {
                return "请勿重复下单";
            }
            // 1. 布隆过滤器判断商品是否存在（伪代码，实际用Redis或Guava实现）
            Boolean mightExist = redisTemplate.opsForValue().getBit(BLOOM_KEY, skuId);
            if (mightExist == null || !mightExist) {
                return "商品不存在";
            }
            // 2. 预减库存
            Long stock = redisTemplate.opsForValue().decrement(STOCK_KEY + skuId);
            if (stock == null || stock < 0) {
                return "已售罄";
            }
            // 3. 异步写入Kafka
            String orderMsg = userId + "," + skuId;
            kafkaTemplate.send(ORDER_TOPIC, orderMsg);
            // 4. 标记用户已抢购（防重复）
            redisTemplate.opsForValue().setBit("seckill:order:" + userId + ":" + skuId, 0, true);
            // 写本地消息表
            OrderMsg msg = new OrderMsg();
            msg.setUserId(userId);
            msg.setSkuId(skuId);
            msg.setStatus(0);
            msg.setCreateTime(java.time.LocalDateTime.now());
            orderMsgMapper.insert(msg);
        } catch (Exception e) {
            // 补偿机制：回滚库存、删除幂等key、记录异常日志
            redisTemplate.opsForValue().increment(STOCK_KEY + skuId);
            redisTemplate.delete(IDEMPOTENT_KEY + userId + ":" + skuId);
            throw new RuntimeException("下单失败，已补偿", e);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
        return "抢购成功，订单处理中";
    }
} 
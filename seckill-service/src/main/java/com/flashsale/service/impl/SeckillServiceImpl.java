package com.flashsale.service.impl;

import com.flashsale.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {
    private final StringRedisTemplate redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String STOCK_KEY = "seckill:stock:";
    private static final String BLOOM_KEY = "seckill:bloom";
    private static final String ORDER_TOPIC = "seckill-order";

    @Override
    public String seckill(Long userId, Long skuId) {
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
        return "抢购成功，订单处理中";
    }
} 
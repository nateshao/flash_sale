package com.flashsale.canal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 伪代码：Canal监听MySQL binlog，订单表变更时同步缓存
 * 实际生产建议用canal-client或MQ消费binlog事件
 */
@Slf4j
@Component
public class CanalCacheSyncListener {
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 伪方法，实际应由Canal客户端回调
    public void onOrderTableChange(Long skuId, Long newStock) {
        String key = "seckill:stock:" + skuId;
        redisTemplate.opsForValue().set(key, String.valueOf(newStock));
        log.info("[Canal] 同步库存到Redis: {} -> {}", key, newStock);
    }
} 
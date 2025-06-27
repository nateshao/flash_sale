package com.flashsale.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisBloomConfig implements CommandLineRunner {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) {
        // 伪布隆过滤器：将skuId为1~10000的商品置为存在
        for (long skuId = 1; skuId <= 10000; skuId++) {
            redisTemplate.opsForValue().setBit("seckill:bloom", skuId, true);
        }
    }
} 
package com.flashsale.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockPreheatConfig implements CommandLineRunner {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) {
        // 预热10000个商品，每个库存1000
        for (long skuId = 1; skuId <= 10000; skuId++) {
            redisTemplate.opsForValue().set("seckill:stock:" + skuId, "1000");
        }
    }
} 
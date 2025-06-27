package com.flashsale.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SeckillServiceTest {
    @Autowired
    private SeckillService seckillService;

    @Test
    public void testSeckill() {
        String result = seckillService.seckill(1L, 1L);
        assertNotNull(result);
    }
} 
package com.flashsale;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class SeckillPressureTest {
    @Test
    public void pressureTest() throws InterruptedException {
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < threadCount; i++) {
            final int userId = i + 1;
            new Thread(() -> {
                String url = "http://localhost:8080/seckill/order?userId=" + userId + "&skuId=1";
                String resp = restTemplate.postForObject(url, null, String.class);
                System.out.println(resp);
                latch.countDown();
            }).start();
        }
        latch.await();
    }
} 
package com.flashsale.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockApiController {
    @GetMapping("/order-status")
    public Map<String, Object> orderStatus() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("orderId", 123456789L);
        resp.put("status", "SUCCESS");
        resp.put("msg", "模拟订单已支付");
        return resp;
    }

    @GetMapping("/user-info")
    public Map<String, Object> userInfo() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("userId", 1001L);
        resp.put("nickname", "mock用户");
        resp.put("vip", true);
        return resp;
    }

    @GetMapping("/product-detail")
    public Map<String, Object> productDetail() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("skuId", 1L);
        resp.put("title", "爆款秒杀商品");
        resp.put("price", 99.99);
        resp.put("stock", 1000);
        return resp;
    }

    @GetMapping("/order-list")
    public Map<String, Object> orderList() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("orders", java.util.Arrays.asList(
            Map.of("orderId", 123L, "status", "SUCCESS"),
            Map.of("orderId", 124L, "status", "FAIL")
        ));
        return resp;
    }
} 
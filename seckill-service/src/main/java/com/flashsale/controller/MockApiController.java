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
} 
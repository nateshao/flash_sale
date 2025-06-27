package com.flashsale.controller;

import com.flashsale.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seckill")
@RequiredArgsConstructor
public class SeckillController {
    private final SeckillService seckillService;

    @PostMapping("/order")
    public String seckill(@RequestParam Long userId, @RequestParam Long skuId) {
        return seckillService.seckill(userId, skuId);
    }
} 
package com.flashsale.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.flashsale.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotspotSeckillController {
    private final SeckillService seckillService;

    @PostMapping("/seckill/hot")
    @SentinelResource(value = "seckillHot", blockHandler = "handleHotBlock")
    public String seckillHot(@RequestParam Long userId, @RequestParam Long skuId) {
        return seckillService.seckill(userId, skuId);
    }
    public String handleHotBlock(Long userId, Long skuId, BlockException ex) {
        return "热点限流中，请稍后再试";
    }
} 
package com.flashsale.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel-demo")
public class SentinelDemoController {
    @GetMapping("/test")
    @SentinelResource(value = "sentinelTest", blockHandler = "handleBlock")
    public String test() {
        return "正常访问";
    }
    public String handleBlock(BlockException ex) {
        return "被限流了";
    }
} 
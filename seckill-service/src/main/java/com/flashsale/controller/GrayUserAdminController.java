package com.flashsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/gray-admin")
public class GrayUserAdminController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/add")
    public String addGrayUser(@RequestParam String userId) {
        redisTemplate.opsForSet().add("gray-users", userId);
        return "ok";
    }
    @PostMapping("/remove")
    public String removeGrayUser(@RequestParam String userId) {
        redisTemplate.opsForSet().remove("gray-users", userId);
        return "ok";
    }
    @GetMapping("/list")
    public Set<String> listGrayUsers() {
        return redisTemplate.opsForSet().members("gray-users");
    }
} 
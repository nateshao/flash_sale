package com.flashsale.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {
    @Autowired
    private Producer captchaProducer;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/captcha")
    public void getCaptcha(@RequestParam String uuid, HttpServletResponse response) throws IOException {
        String code = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(code);
        redisTemplate.opsForValue().set("captcha:" + uuid, code, 2, TimeUnit.MINUTES);
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }
} 
package com.flashsale.interceptor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class IpRateLimiterInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate redisTemplate;
    public IpRateLimiterInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        String key = "seckill:ip:limit:" + ip;
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1) {
            redisTemplate.expire(key, 1, TimeUnit.SECONDS);
        }
        if (count != null && count > 20) { // 每秒限20次
            response.setStatus(429);
            response.getWriter().write("请求过于频繁，请稍后再试");
            return false;
        }
        return true;
    }
} 
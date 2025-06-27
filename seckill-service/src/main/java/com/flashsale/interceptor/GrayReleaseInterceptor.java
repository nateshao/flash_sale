package com.flashsale.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class GrayReleaseInterceptor implements HandlerInterceptor {
    @Value("${feature.gray-release.enabled:false}")
    private boolean grayEnabled;
    @Value("${feature.gray-release.gray-users:}")
    private String grayUsers;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!grayEnabled) return true;
        String userId = request.getParameter("userId");
        if (userId != null && Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("gray-users", userId))) {
            request.setAttribute("isGrayUser", true);
        }
        return true;
    }
} 
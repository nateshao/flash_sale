package com.flashsale.interceptor;

import org.springframework.beans.factory.annotation.Value;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!grayEnabled) return true;
        String userId = request.getParameter("userId");
        List<String> grayList = Arrays.asList(grayUsers.split(","));
        if (userId != null && grayList.contains(userId)) {
            request.setAttribute("isGrayUser", true);
        }
        return true;
    }
} 
package com.flashsale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.Map;

@Configuration
public class MockApiRegistrar {
    @Bean
    public void registerMockApis(RequestMappingHandlerMapping mapping, com.flashsale.controller.MockApiController mockApiController) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        // 这里可扩展自动注册逻辑，Spring Boot 已自动注册所有@RestController方法
        // 只需保证MockApiController被@ComponentScan扫描即可
    }
} 
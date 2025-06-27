package com.flashsale.config;

import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class SentinelConfig {
    @PostConstruct
    public void init() {
        System.setProperty("csp.sentinel.dashboard.server", "localhost:8858");
        System.setProperty("csp.sentinel.api.port", "8719");
    }
} 
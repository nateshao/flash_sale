package com.flashsale.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long userId;
    private Long skuId;
    private LocalDateTime createTime;
} 
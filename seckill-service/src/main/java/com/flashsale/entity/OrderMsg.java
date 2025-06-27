package com.flashsale.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderMsg {
    private Long id;
    private Long userId;
    private Long skuId;
    private Integer status; // 0未发送 1已发送
    private LocalDateTime createTime;
} 
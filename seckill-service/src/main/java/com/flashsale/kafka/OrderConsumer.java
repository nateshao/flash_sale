package com.flashsale.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.flashsale.entity.Order;
import com.flashsale.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

@Component
@Slf4j
public class OrderConsumer {
    @Autowired
    private OrderMapper orderMapper;

    @KafkaListener(topics = "seckill-order", groupId = "seckill-group")
    public void consume(String message) {
        // message: userId,skuId
        log.info("收到下单消息: {}", message);
        try {
            String[] arr = message.split(",");
            Long userId = Long.valueOf(arr[0]);
            Long skuId = Long.valueOf(arr[1]);
            Order order = new Order();
            order.setUserId(userId);
            order.setSkuId(skuId);
            order.setCreateTime(LocalDateTime.now());
            orderMapper.insert(order);
            log.info("订单入库成功: {}", order);
        } catch (Exception e) {
            log.error("订单入库失败，消息: {}", message, e);
            // TODO: 可写入死信队列
        }
    }
} 
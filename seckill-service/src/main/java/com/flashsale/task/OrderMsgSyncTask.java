package com.flashsale.task;

import com.flashsale.entity.OrderMsg;
import com.flashsale.mapper.OrderMsgMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMsgSyncTask {
    private final OrderMsgMapper orderMsgMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String ORDER_TOPIC = "seckill-order";

    @Scheduled(fixedDelay = 5000)
    public void syncUnsentMsgs() {
        List<OrderMsg> msgs = orderMsgMapper.selectUnsent();
        for (OrderMsg msg : msgs) {
            try {
                String orderMsg = msg.getUserId() + "," + msg.getSkuId();
                kafkaTemplate.send(ORDER_TOPIC, orderMsg);
                orderMsgMapper.markSent(msg.getId());
                log.info("本地消息投递成功: {}", orderMsg);
            } catch (Exception e) {
                log.error("本地消息投递失败: {}", msg, e);
            }
        }
    }
} 
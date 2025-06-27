package com.flashsale.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
@Component
public class CanalClientRunner implements CommandLineRunner {
    @Autowired
    private CanalCacheSyncListener cacheSyncListener;

    @Override
    public void run(String... args) throws Exception {
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("localhost", 11111), "example", "canal", "canal");
        connector.connect();
        connector.subscribe("seckill_order");
        connector.rollback();
        log.info("Canal客户端已连接，开始监听binlog...");
        while (true) {
            Message message = connector.getWithoutAck(100);
            List<CanalEntry.Entry> entries = message.getEntries();
            for (CanalEntry.Entry entry : entries) {
                if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                    CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                    for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                        Long skuId = null;
                        Long newStock = null;
                        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                            if (column.getName().equals("sku_id")) {
                                skuId = Long.valueOf(column.getValue());
                            }
                            if (column.getName().equals("stock")) {
                                newStock = Long.valueOf(column.getValue());
                            }
                        }
                        if (skuId != null && newStock != null) {
                            cacheSyncListener.onOrderTableChange(skuId, newStock);
                        }
                    }
                }
            }
            connector.ack(message.getId());
            Thread.sleep(1000);
        }
    }
} 
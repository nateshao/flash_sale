# Flash Sale 秒杀系统 DEMO

## 架构概览

- Spring Boot Java 服务（核心秒杀逻辑）
- Redis（缓存、库存、布隆过滤器）
- Kafka（异步削峰、下单消息）
- MySQL（订单数据）
- Docker Compose 一键启动环境
- 可观测性（Prometheus、Grafana、ELK 可选）

## 目录结构

```
flash_sale/
  ├── seckill-service/      # Java Spring Boot 服务
  ├── docker-compose.yml    # 一键启动环境
  ├── README.md             # 说明文档
  └── ...
```

## 启动方式

1. 安装 Docker & Docker Compose
2. `docker-compose up -d` 启动全部依赖
3. 启动 seckill-service（可用IDE或`mvn spring-boot:run`）
4. 访问接口进行压测

## 主要功能

- 秒杀下单接口（高并发、限流、异步削峰）
- Redis 缓存库存、布隆过滤器防穿透
- Kafka 异步下单、死信队列
- MySQL 订单落库
- 可选：Prometheus 监控、ELK 日志

---

详细模块和代码见各子目录说明。 
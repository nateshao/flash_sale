# 🚀 Flash Sale 秒杀系统 DEMO

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen)](https://spring.io/projects/spring-boot)
[![Redis](https://img.shields.io/badge/Redis-6.2-red)](https://redis.io/)
[![Kafka](https://img.shields.io/badge/Kafka-2.7.0-black)](https://kafka.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![SkyWalking](https://img.shields.io/badge/SkyWalking-9.0.0-purple)](https://skywalking.apache.org/)
[![Prometheus](https://img.shields.io/badge/Prometheus-2.0-orange)](https://prometheus.io/)
[![Grafana](https://img.shields.io/badge/Grafana-9.0-yellow)](https://grafana.com/)

---

## 📚 目录
- [系统架构](#系统架构)
- [功能模块](#功能模块)
- [快速启动](#快速启动)
- [监控与链路追踪](#监控与链路追踪)
- [目录结构](#目录结构)
- [示意图](#示意图)
- [致谢](#致谢)

---

## 🏗️ 系统架构

```
                 ┌────────────┐
                 │ 用户请求（亿级）│
                 └────┬───────┘
                      ▼
             ┌────────────────┐
             │ CDN + SLB       │ ← 限流/防刷
             └────┬───────────┘
                  ▼
         ┌───────────────────────┐
         │ 应用层（Java 服务集群） │ ← 核心服务
         └────┬─────────────┬────┘
              ▼             ▼
    ┌────────────────┐ ┌───────────────┐
    │ 缓存层（Redis） │ │ 消息队列（MQ）│ ← 流量削峰
    └────────────────┘ └───────────────┘
              ▼
       ┌──────────────┐
       │ 数据层（MySQL / NoSQL）│
       └──────────────┘
```

---

## 🧩 功能模块

- **高并发秒杀接口**：Redis 预减库存、布隆过滤器防穿透、Kafka 异步下单
- **限流与熔断**：Sentinel 注解与 Dashboard 动态规则
- **异步削峰**：Kafka 消息队列，死信队列预留
- **数据库抗压**：MySQL 分库分表、读写分离（可扩展）
- **全链路追踪**：SkyWalking Agent 零侵入接入
- **监控与告警**：Prometheus + Grafana，JVM/Redis/Kafka/MySQL/业务QPS
- **日志采集**：Loki + Promtail，日志可视化检索
- **自动扩缩容**：K8s Deployment + HPA

---

## ⚡ 快速启动

1. **准备依赖**
   - 安装 [Docker](https://www.docker.com/) & [Docker Compose](https://docs.docker.com/compose/)
   - 安装 [JDK 17+](https://adoptopenjdk.net/)
2. **一键启动中间件**
   ```sh
   docker-compose up -d
   ```
3. **初始化数据库**
   - 连接 MySQL，执行 `seckill-service/src/main/resources/schema.sql`
4. **构建并启动 Java 服务**
   ```sh
   cd seckill-service
   mvn clean package -DskipTests
   java -javaagent:../skywalking-agent/skywalking-agent.jar \
        -DSW_AGENT_NAME=seckill-service \
        -DSW_AGENT_COLLECTOR_BACKEND_SERVICES=host.docker.internal:11800 \
        -jar target/seckill-service.jar
   ```
5. **压测/体验**
   - Postman/JMeter 调用 `/seckill/order` 或运行 `SeckillPressureTest`

---

## 📈 监控与链路追踪

- **SkyWalking UI**：[http://localhost:8088](http://localhost:8088)
- **Prometheus**：[http://localhost:9090](http://localhost:9090)
- **Grafana**：[http://localhost:3000](http://localhost:3000)（导入 `grafana-seckill-dashboard.json`）
- **Sentinel Dashboard**：[http://localhost:8858](http://localhost:8858)
- **Loki 日志检索**：Grafana 配置 Loki 数据源，查询 `{job="seckill"}`

---

## 📁 目录结构

```
flash_sale/
  ├── seckill-service/           # Java Spring Boot 服务
  │     ├── src/
  │     ├── Dockerfile
  │     └── ...
  ├── skywalking-agent/          # SkyWalking Agent 目录（需手动下载）
  ├── prometheus.yml             # Prometheus 配置
  ├── promtail-config.yml        # Promtail 配置
  ├── grafana-seckill-dashboard.json # Grafana 仪表盘
  ├── k8s-deployment.yaml        # K8s 部署与HPA
  ├── docker-compose.yml         # 一键启动环境
  └── README.md
```

---

## 🗺️ 示意图

**Redis 缓存结构**
```
商品库存:      seckill:stock:{skuId}
用户抢购标记:  seckill:order:{userId}:{skuId}
布隆过滤器:    seckill:bloom
商品详情缓存:  seckill:sku:{skuId}
```

---

## 🙏 致谢
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Redis](https://redis.io/)
- [Kafka](https://kafka.apache.org/)
- [MySQL](https://www.mysql.com/)
- [SkyWalking](https://skywalking.apache.org/)
- [Prometheus](https://prometheus.io/)
- [Grafana](https://grafana.com/)
- [Loki](https://grafana.com/oss/loki/)
- [Sentinel](https://github.com/alibaba/Sentinel)

---

> 本项目为高并发秒杀系统 DEMO，适合架构学习、面试、压测与分布式实战演练。 
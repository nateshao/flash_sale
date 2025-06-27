# SkyWalking Agent 使用说明

1. 访问 [SkyWalking 官网](https://skywalking.apache.org/downloads/) 下载 skywalking-agent 压缩包。
2. 解压后将 `skywalking-agent` 目录放到本项目同级目录下（或自定义路径）。
3. 启动 Java 服务时添加如下 JVM 参数：

```
-javaagent:/path/to/skywalking-agent/skywalking-agent.jar \
-DSW_AGENT_NAME=seckill-service \
-DSW_AGENT_COLLECTOR_BACKEND_SERVICES=host.docker.internal:11800
```

4. Docker 部署可参考 seckill-service/Dockerfile 中的集成方式。 
#!/bin/bash
# 灰度分流curl自动化测试脚本

HOST="http://localhost"
API="/seckill/order"
USERIDS=(1001 1002 2001 2002 3001)

# 1. 健康检查
for id in "${USERIDS[@]}"; do
  echo -n "[Health] userId=$id: "
  curl -s -o /dev/null -w "%{http_code}\n" "$HOST$API?userId=$id"
  sleep 0.2
done

echo "\n--- 灰度用户动态切换测试 ---"
# 2. 加入灰度用户
redis-cli SADD gray-users 2001

for id in "${USERIDS[@]}"; do
  echo -n "[GrayTest] userId=$id: "
  curl -s "$HOST$API?userId=$id"
  sleep 0.2
done

# 3. 移除灰度用户
redis-cli SREM gray-users 2001

echo "\n--- 流量分布统计 ---"
GRAY=0
PROD=0
for i in {1..20}; do
  resp=$(curl -s "$HOST$API?userId=2001")
  if [[ $resp == *"gray"* ]]; then
    ((GRAY++))
  else
    ((PROD++))
  fi
  sleep 0.1
done

echo "灰度服务命中: $GRAY 次"
echo "正式服务命中: $PROD 次" 
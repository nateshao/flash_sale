from locust import HttpUser, task, between
import random

class SeckillUser(HttpUser):
    wait_time = between(0.01, 0.05)
    @task
    def seckill(self):
        user_id = random.randint(1, 10000000)
        self.client.post("/seckill/order", params={"userId": user_id, "skuId": 1})

# 启动主控节点：
# locust -f locustfile.py --master --host=http://your-seckill-service
# 启动工作节点：
# locust -f locustfile.py --worker --master-host=主控IP 
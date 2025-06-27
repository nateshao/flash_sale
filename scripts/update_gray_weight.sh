#!/bin/bash
# 用法: ./update_gray_weight.sh 30
WEIGHT=$1
kubectl annotate ingress seckill-ingress-gray nginx.ingress.kubernetes.io/canary-weight="$WEIGHT" --overwrite 
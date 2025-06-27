#!/bin/bash
# 导出OpenAPI 3.0 YAML
curl -H "Accept: application/yaml" http://localhost:8080/v3/api-docs > openapi.yaml 
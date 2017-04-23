#!/usr/bin/env bash

docker tag registry.cn-beijing.aliyuncs.com/stardust/machine-registry-service registry.cn-beijing.aliyuncs.com/stardust/machine-registry-service:latest
docker push registry.cn-beijing.aliyuncs.com/stardust/machine-registry-service:latest

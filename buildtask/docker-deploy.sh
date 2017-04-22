#!/usr/bin/env bash

TARGET_SERVER=$1
ENV_VARS="[\"APP_ENV=${APP_ENV}\",\"DB_SERVER=${DB_SERVER}\",\"DB_PORT=3306\",\"DB_USER=${DB_USER}\",\"DB_PASSWORD=${DB_PASSWORD}\",\"REDIS_SERVER=${REDIS_SERVER}\",\"REDIS_PORT=6379\",\"REDIS_PASS=${REDIS_PASS}\", \"WX_NOTIFY_BASE_URL=${WX_NOTIFY_BASE_URL}\"]"
DOCKER_IMAGE="registry.cn-beijing.aliyuncs.com/trainingjoy/transaction-service"
HOST_CONFIG="{\"PortBindings\":{\"7170/tcp\": [{ \"HostPort\": \"7170\"}]}}"
CONTAINER_CONFIG="{\"Hostname\":\"transaction-service\",\"Name\": \"transaction-service\",\"Image\": \"$DOCKER_IMAGE\", \"Env\":$ENV_VARS, \"ExposedPorts\": {\"7170/tcp\": {}}, \"HostConfig\": $HOST_CONFIG}"
#pull image
curl -v -f -X POST "http://$TARGET_SERVER:2376/images/create?fromImage=$DOCKER_IMAGE"
curl -v -H "Content-type: application/json" -X DELETE "http://$TARGET_SERVER:2376/containers/transaction-service?force=true"
curl -v -f -H "Content-type: application/json" -X POST -d "$CONTAINER_CONFIG" "http://$TARGET_SERVER:2376/containers/create?name=transaction-service"
curl -v -f -H "Content-type: application/json" -X POST "http://$TARGET_SERVER:2376/containers/transaction-service/start"
#!/usr/bin/env bash

CMD=$1

if [ ! -n "$CMD" ] ;then
    CMD=./entrypoint.sh
fi

docker rm -f machine-registry-service || echo "No started service found"

docker run -t -p 9098:9098 --rm --name=machine-registry-service \
         -e APP_ENV=${APP_ENV} \
         -e DB_HOST=${DB_HOST} \
         -e DB_PORT=${DB_PORT} \
         -e DB_USERNAME=${DB_USER} \
         -e DB_PASSWORD=${DB_PASSWORD} \
         registry.cn-beijing.aliyuncs.com/stardust/machine-registry-service \
         $CMD &
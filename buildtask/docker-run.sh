#!/usr/bin/env bash

DB_SERVER=${DB_SERVER}
DB_PORT=${DB_PORT}
APP_ENV=${APP_ENV}
CMD=$1

if  [ ! -n "$DB_SERVER" ] ;then
    DB_SERVER='127.0.0.1'
fi

if  [ ! -n "$DB_PORT" ] ;then
    DB_PORT=1433
fi

if [ ! -n "$CMD" ] ;then
    CMD=./entrypoint.sh
fi

docker rm -f machine-registry || echo "No started transaction service found"

docker run -t -p 9098:9098 --rm --name=machine-registry \
         -e APP_ENV=$APP_ENV \
         -e DB_HOST=$DB_SERVER \
         -e DB_PORT=$DB_PORT \
         -e DB_USERNAME=$DB_USERNAME
         -e DB_PASSWORD=$DB_PASSWORD
         registry-vpc.cn-beijing.aliyuncs.com/stardustio/machine-registry \
         $CMD &
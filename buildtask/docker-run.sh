#!/usr/bin/env bash

DB_SERVER=${TS_DB_SERVER}
DB_PORT=${TS_DB_PORT}
SESSION_SERVER=${TS_SESSION_SERVER}
SESSION_PORT=${TS_SESSION_PORT}
PENDING_ORDER_SERVER=${TS_PENDING_ORDER_SERVER}
APP_ENV=${APP_ENV}
CMD=$1

if  [ ! -n "$DB_SERVER" ] ;then
    DB_SERVER='127.0.0.1'
fi

if  [ ! -n "$SESSION_SERVER" ] ;then
    SESSION_SERVER='127.0.0.1'
fi

if  [ ! -n "$PENDING_ORDER_SERVER" ] ;then
    PENDING_ORDER_SERVER=$SESSION_SERVER
fi

if  [ ! -n "$DB_PORT" ] ;then
    DB_PORT=3306
fi

if  [ ! -n "$SESSION_PORT" ] ;then
    SESSION_PORT=6379
fi

if [ ! -n "$CMD" ] ;then
    CMD=./entrypoint.sh
fi

docker rm -f transaction-service || echo "No started transaction service found"

docker run -t -p 7170:7170 --rm --name=transaction-service \
         -e APP_ENV=$APP_ENV \
         -e TS_DB_SERVER=$DB_SERVER \
         -e TS_DB_PORT=$DB_PORT \
         -e TS_SESSION_SERVER=$SESSION_SERVER \
         -e TS_PENDING_ORDER_SERVER=$PENDING_ORDER_SERVER \
         -e TS_SESSION_PORT=$SESSION_PORT \
         registry-vpc.cn-beijing.aliyuncs.com/stardustio/transaction-service \
         $CMD &
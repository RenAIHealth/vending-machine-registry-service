#!/usr/bin/env bash

DB_ENV=""
if [[ $1 -eq 'dev' ]]; then
    DB_ENV="_development"
else
    DB_ENV=""
fi
flyway -baselineOnMigrate=true -user=root -password= -url=jdbc:mysql://localhost:3306/transactions"$DB_ENV" -locations=filesystem:./buildtask/schema/ migrate
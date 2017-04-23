## Vending Machine Registry Service
Supply registry and tracking service for vending machine

## 1. Dependencies
```
Java 8
MSSQL
Maven
Git
```

### 2. Documentation
```
- raml - ./doc/raml/rest-api.raml
```

### 3. Build docker image
```shell
./buildtask/docker-build.sh
```

### 4. Run in docker container
- prod
  - export APP_ENV=prod
  - export DB_HOST=`url of db host`
  - export DB_PORT=`port of db host`
  - epxort DB_USER=`db user`
  - export DB_PASSWORD=`db password`
- dev
  - export APP_ENV=dev
- test
  - export APP_ENV=test

```shell
./buildtask/docker-run.sh
```
### 5. Run test locally
```shell
./buildtask/test.sh
```



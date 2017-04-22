## Transaction Service
Micro-Service to proceed training transactions

## Dependencies
```
Java 8
MySQL
Redis
Maven
Git
```

## Setup local environment
### 1. Checkout and build
```sh
git clone https://github.com/TrainingJoy/TransactionService.git
export APP_ENV=dev
mvn clean install -Dmaven.test.skip=true
```
### 2. Install and start redis
```sh
brew install redis
redis-server
```
### 3. Run test
```sh
./buildtask/test.sh
```

### 4. Startup service locally
- Command line:
```sh
export APP_ENV=dev
java -jar ./target/trainingjoy-transaction-0.0.1.jar

```
- IntelliJ
```
1). Run->Edit Configuration->Springboot->TransactionServiceApplication
2). Add environment APP_ENV=dev
3). Run/Debug TransactionServiceApplication
```

### 5. CI
http://112.74.57.47:1338/job/transaction-service/

### 6. Documentation
```
- raml - ./doc/raml/rest-api.raml
```

### 7. Docker
- base image: stardustdocker/java8
- OS: CentOS 7.2
- Java: version 8
- ENV: APP_ENV, TS_DB_SERVER, TS_DB_PORT, TS_SESSION_SERVER, TS_SESSION_PORT



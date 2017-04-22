export APP_ENV='test'
mvn test

export APP_ENV=$1
mvn clean install -Dmaven.test.skip=true
java -jar ./target/machine-registry-0.0.1.jar

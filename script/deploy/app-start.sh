#!/usr/bin/env bash
cd /home/travis/zealsay/zealsay_backend
echo start package project...
mvn clean package -Dmaven.test.skip=true
echo package OK...
ls /app/target/
java -Djava.security.egd=file:/dev/./urandom -jar /app/target/zealsay-1.0.0.jar

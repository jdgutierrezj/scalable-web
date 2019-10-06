#!/bin/bash
yum update -y
yum install java-1.8.0-openjdk.x86_64 maven git -y
git clone https://github.com/jdgutierrezj/scalable-web
cd scalable-web
mvn clean install
java -jar target/scalable-web-1.0.0.jar -Dspring.profiles.active=prod

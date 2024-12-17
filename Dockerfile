FROM openjdk:17-jdk-slim

EXPOSE 8084

ARG JAR_FILE=target/notification-service.jar
ADD ${JAR_FILE} notification-service.jar

ENTRYPOINT exec java -jar /notification-service.jar
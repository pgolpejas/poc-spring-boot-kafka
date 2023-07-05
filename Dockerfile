FROM eclipse-temurin:17.0.7_7-jre-alpine
ARG VERSION
ARG JAR_FILE=poc-spring-boot-kafka-$VERSION.jar

ADD ${JAR_FILE} poc-spring-boot-kafka.jar

EXPOSE 8080 8081 9999
ENTRYPOINT java -jar poc-spring-boot-kafka.jar

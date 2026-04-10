FROM eclipse-temurin:21-jdk-alpine
RUN apk add --no-cache curl
VOLUME /tmp
COPY target/service-registry-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

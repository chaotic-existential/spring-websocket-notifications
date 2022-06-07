FROM openjdk:17.0.2-oracle
EXPOSE 8080
COPY build/libs/spring-websocket-notifications-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
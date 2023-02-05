FROM maven:3.8-openjdk-17 AS build
COPY src src
COPY pom.xml .
RUN mvn -f pom.xml clean package

FROM openjdk:17
COPY --from=build target/quiz-portal_backend-0.0.1-SNAPSHOT.jar quiz-portal-app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/quiz-portal-app.jar"]
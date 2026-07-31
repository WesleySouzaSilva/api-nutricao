FROM maven:3.8-openjdk-11-slim AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests -q

FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /build/target/api-nutricao-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

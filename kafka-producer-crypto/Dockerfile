# Stage 1: Build the child project
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests -pl kafka-producer-crypto -am

# Stage 2: Prepare the final image for the child module
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/kafka-producer-crypto/target/kafka-producer-crypto-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

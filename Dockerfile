# Stage 1: Build the parent project and child modules
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Prepare the final image for the parent module
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install netcat
RUN apt-get update && apt-get install -y netcat

# Copy the built jar from the build stage
COPY --from=build /app/kafka-producer-crypto/target/kafka-producer-crypto-1.0-SNAPSHOT.jar app.jar

# Copy the wait-for script to the final image
COPY wait-for.sh /app/wait-for.sh
RUN chmod +x /app/wait-for.sh

EXPOSE 8080

# Use the wait-for script to wait for MongoDB to be ready
CMD ["./wait-for.sh", "mongo", "java", "-jar", "app.jar"]

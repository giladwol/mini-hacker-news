# This docker file should build Spring and run it along with MongoDB

# Use the official maven/Java 17 image to create a build artifact.
FROM maven:3.8.3-openjdk-17-slim AS build

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package

# Use the official OpenJDK image to create a runtime image.
FROM khipu/openjdk17-alpine:latest

# Copy the jar to the production image from the builder stage.
COPY --from=build /app/target/mini-hacker-java-0.0.1-SNAPSHOT.jar /mini-hacker-java-0.0.1-SNAPSHOT.jar

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/mini-hacker-java-0.0.1-SNAPSHOT.jar"]

# Expose the port
EXPOSE 8080

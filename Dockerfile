# Use the Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Package the application, skipping the tests
RUN mvn clean package -DskipTests

# Use a slim version of OpenJDK 17 for the runtime environment
FROM openjdk:17.0.1-jdk-slim

# Set the working directory for the runtime image
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /app/target/JWT-0.0.1-SNAPSHOT.jar /app/jwt.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "jwt.jar"]

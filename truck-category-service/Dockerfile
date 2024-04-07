# Base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/truck-category-service.jar /app

# Expose the port that the Spring Boot application will run on
EXPOSE 8190

# Specify the command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "truck-category-service.jar"]

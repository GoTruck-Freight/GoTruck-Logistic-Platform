# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container at /app
COPY build/libs/shipper-service-0.0.1-SNAPSHOT.jar /app/shipper-service.jar

# Copy the env.properties and application.yml files into the container at /app
COPY src/main/resources/application.yml /app/application.yml
COPY src/main/resources/env.properties /app/env.properties

# Expose port 9091
EXPOSE 9091

# Specify the command to run your application
CMD ["java", "-jar", "shipper-service.jar"]

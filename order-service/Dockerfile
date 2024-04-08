# Base image
FROM adoptopenjdk:17-jdk-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file from the host to the container
COPY target/order-service.jar /app

# Copy the env.properties file containing sensitive information
COPY env.properties /app

# Expose the port the application runs on
EXPOSE 7070

# Command to run the application
CMD ["java", "-jar", "order-service.jar", "--spring.config.location=file:/app/env.properties"]

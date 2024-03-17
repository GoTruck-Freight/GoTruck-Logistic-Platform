# Use the official OpenJDK image as base image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper scripts and the project files into the container
COPY .mvn/ .mvn/
COPY mvnw ./
COPY mvnw.cmd ./
COPY pom.xml ./
COPY src ./src

# Create a config directory in the container
RUN mkdir -p /app/config

# Copy the configuration file into the container at /app/config
COPY src/main/resources/env.properties /app/config/

# Environment variable set et
ENV UPLOAD_DIR=/app/static/images/ProfileImage/
ENV DEFAULT_IMAGE_PATH=/app/images/driverrr-image.jpeg

# Run Maven command to download project dependencies
RUN ./mvnw -N io.takari:maven:wrapper -Dmaven=3.8.4

# Download project dependencies using Maven wrapper
RUN ./mvnw dependency:go-offline

# Build the application using Maven wrapper
RUN ./mvnw package

# Expose port 9091 to the outside world
EXPOSE 9091

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=production
ENV SPRING_CONFIG_LOCATION=/app/config/
ENV JAVA_OPTS=""
ENV ENV_FILE_PATH=/app/config/env.properties

# Define the command to run the Spring Boot application using Maven Wrapper
ENTRYPOINT ["sh", "-c", "./mvnw spring-boot:run -Dspring.config.location=file:/app/config/application.yml -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE"]

# Use the official OpenJDK image as base image
FROM openjdk:17

  # Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper scripts and the project files into the container
COPY mvnw mvnw.cmd .mvn/ ./
COPY pom.xml ./

# Download project dependencies using Maven wrapper
RUN ./mvnw dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application using Maven wrapper
RUN ./mvnw package
  
  # Copy the .env file into the container at /app
COPY src/main/resources/env.properties /app/env.properties
  
  # Expose port 9091 to the outside world
EXPOSE 9091
  
  # Define environment variables
ENV SPRING_PROFILES_ACTIVE=production
ENV SPRING_CONFIG_LOCATION=/app/config/
ENV JAVA_OPTS=""
ENV ENV_FILE_PATH=/app/env.properties
  
  # Start the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar -Dspring.config.location=$SPRING_CONFIG_LOCATION -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE /app/shipper-service.jar --spring.config.additional-location=file:${ENV_FILE_PATH}"]

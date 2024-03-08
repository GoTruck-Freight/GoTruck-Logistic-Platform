# Base image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper scripts and the project files into the container
COPY .mvn/ .mvn/
COPY mvnw ./
COPY mvnw.cmd ./
COPY pom.xml ./
COPY src ./src

# Run Maven command to initialize Maven wrapper (if not already initialized)
RUN ./mvnw -N io.takari:maven:wrapper -Dmaven=3.8.4

# Download project dependencies using Maven wrapper
RUN ./mvnw dependency:go-offline

# Build the application using Maven wrapper
RUN ./mvnw package -DskipTests

# Expose port 8761 to the outside world
EXPOSE 8761

# Define the command to run the Spring Boot application using Maven Wrapper
CMD ["./mvnw", "spring-boot:run", "-Dspring.config.location=file:/app/config/application.yml", "-Dspring.profiles.active=production"]

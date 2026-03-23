# Use Java 17 (since you are using JavaSE-17)
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy jar file (after build)
COPY target/*.jar app.jar

# Expose port (Spring Boot default)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
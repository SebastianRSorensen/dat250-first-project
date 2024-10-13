# Step 1: Build Stage
FROM gradle:8.10.2-jdk21 AS build
WORKDIR /app

# Copy the Gradle wrapper and build scripts first to leverage Docker cache
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle /app/gradle

# Download dependencies (caching step)
RUN ./gradlew build --no-daemon --parallel --continue || return 0

# Now copy the rest of the application source code
COPY src ./src

# Build the Spring Boot application and generate the JAR
RUN ./gradlew bootJar --no-daemon

# Step 2: Runtime Stage
FROM eclipse-temurin:21-jdk-jammy AS runtime
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Create a non-root user for security
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

# Expose the backend port (e.g., 8080)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]

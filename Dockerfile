# Multi-stage Dockerfile for Maven-based Java Testing Project (Rest Assured + JUnit 5)

# Stage 1: Build Stage 
# Compile the Java project and resolve dependencies in a containerized environment. 
# Tests are skipped during the build to avoid failures blocking image creation.

# Use an official Maven image with JDK 21 based on Alpine Linux for a lightweight build environment
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

# Sets /app as the working directory inside the container
# All commands will be executed from this directory, and files will be copied here
WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Download dependencies without building the project to speed up subsequent builds when source code changes but dependencies do not
RUN mvn dependency:resolve

# Copy source code into the container. 
# This includes all Java source files and test files needed for compilation and testing.
COPY src ./src

# Copy .env file for environment configuration
# The dotenv-java library will read BASE_URL from this file
COPY .env* ./

# Creates a directory for Allure test results
# Runs `mvn clean package` to compile the code and package it into a JAR file
# Configures Allure to output test results to /app/allure-results
# Sets Maven logging level to info to reduce verbosity while still showing important information
RUN mkdir -p /app/allure-results && \
    mvn clean package \
    -DskipTests=true \
    -Dallure.results.directory=/app/allure-results \
    -Dorg.slf4j.simpleLogger.defaultLogLevel=info

# Stage 2: Runtime Stage 

# Starts a minimal runtime image with JDK 21 based on Alpine Linux to run the tests.
FROM eclipse-temurin:21-jdk-alpine

# Sets /app as the working directory for the runtime stage.
WORKDIR /app

# Install Maven from Alpine repositories to run tests in the runtime stage.
RUN apk add --no-cache maven

# Copy built artifacts and test results from builder stage
COPY --from=builder /app/target ./target
COPY --from=builder /app/allure-results ./allure-results
COPY --from=builder /app/pom.xml .
COPY --from=builder /app/src ./src
COPY --from=builder /app/.env* ./

# Create non-root user for security
RUN addgroup -g 1000 testuser && \
    adduser -D -u 1000 -G testuser testuser

# Change ownership of the application files to the non-root user to ensure proper permissions when running the container
RUN chown -R testuser:testuser /app

# Switches to the non-root user to run the application.
USER testuser

# Health check to ensure the container is running and Maven is available before executing tests
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD mvn --version || exit 1

# Default command: Run tests
CMD ["mvn", "clean", "test", "-Dallure.results.directory=/app/allure-results"]
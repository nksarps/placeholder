# Multi-stage Dockerfile for Maven-based Java Testing Project (Rest Assured + JUnit 5)

# Stage 1: Build Stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Download dependencies (this layer is cached if pom.xml hasn't changed)
RUN mvn dependency:resolve

# Copy source code
COPY src ./src

# Copy .env file for environment configuration
# The dotenv-java library will read BASE_URL from this file
COPY .env* ./

# Build and run tests, generate Allure reports
# BASE_URL will be read from .env file by dotenv-java
RUN mvn clean test \
    -DskipTests=false \
    -Dallure.results.directory=/app/allure-results \
    -Dorg.slf4j.simpleLogger.defaultLogLevel=info

# Stage 2: Runtime Stage (minimal image for test results)
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Install Maven in runtime stage (needed if running tests dynamically)
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

# Set permissions
RUN chown -R testuser:testuser /app

USER testuser

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD mvn --version || exit 1

# Default command: Run tests
CMD ["mvn", "clean", "test", "-Dallure.results.directory=/app/allure-results"]








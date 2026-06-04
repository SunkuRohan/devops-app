# Stage 1 — Build
FROM gradle:8.7-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2 — Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create non-root user for security best practice
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/build/libs/devops-app-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

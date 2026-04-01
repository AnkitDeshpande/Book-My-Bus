# ── Stage 1: Build ──────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
RUN chmod +x mvnw

# Download dependencies first (better layer caching)
RUN ./mvnw dependency:go-offline -q

# Copy source and build
COPY src src
RUN ./mvnw clean package -DskipTests -q

# ── Stage 2: Run ────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/book-my-bus-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

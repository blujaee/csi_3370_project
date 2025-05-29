# syntax=docker/dockerfile:1

################################################################################
FROM maven:3.8.8-jdk-17-slim AS deps
WORKDIR /build
COPY mvnw .mvn/ pom.xml ./
RUN chmod +x mvnw \
    && ./mvnw dependency:go-offline -B -DskipTests

################################################################################
FROM deps AS builder
WORKDIR /build
COPY src ./src
RUN ./mvnw package -B -DskipTests \
    && mv target/*.jar app.jar

################################################################################
FROM eclipse-temurin:17-jre-jammy AS final
WORKDIR /app
COPY --from=builder /build/app.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

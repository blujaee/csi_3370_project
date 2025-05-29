# build stage
FROM maven:3.8.8-openjdk-17-slim   AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./srcgit
RUN mvn clean package -DskipTests

# runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java","-jar","app.jar"]

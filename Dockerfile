FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY .env .
COPY src/ src/
RUN mvn clean package -DskipTests -e

FROM maven:3.8.5-openjdk-17-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
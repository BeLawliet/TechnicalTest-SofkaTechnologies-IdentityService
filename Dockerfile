FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY ./pom.xml .
COPY ./.mvn ./.mvn
COPY ./mvnw .

RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/identity-service-v0.0.1.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "identity-service-v0.0.1.jar"]

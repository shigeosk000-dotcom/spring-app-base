FROM eclipse-temurin:17 AS builder
WORKDIR /app

# Copy Maven Wrapper
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x ./mvnw

# Download dependencies and build
COPY pom.xml .
RUN ./mvnw -q -B dependency:go-offline

COPY src src
RUN ./mvnw -q -B package

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-Dserver.port=10000", "-jar", "/app/app.jar"]

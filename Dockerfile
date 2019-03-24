FROM maven:3.6-jdk-8-alpine AS builder
WORKDIR /usr/src/app

# Copy pom.xml separately to cache maven dependencies
COPY pom.xml .
RUN echo "Downloading maven dependencies..."
RUN mvn clean verify --fail-never &> /dev/null

COPY . .
RUN mvn -DfinalName=app clean verify

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=builder /usr/src/app/target/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

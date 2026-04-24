# Airport Management System - Dockerfile
FROM eclipse-temurin:21-jre-alpine

LABEL description="Airport Management System - Java OOP"
LABEL version="2.0"

WORKDIR /app

COPY AirportManagementSystem.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

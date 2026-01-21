# -------- Build Stage --------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy backend source
COPY backend/ .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests


# -------- Run Stage --------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the generated jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]

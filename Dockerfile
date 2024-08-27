# syntax=docker/dockerfile:1

################################################################################

# Create a stage for resolving and downloading dependencies.
FROM eclipse-temurin:17-jdk-jammy as deps

# Install Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /build

# Copy the mvnw wrapper with executable permissions, if available
COPY mvnw ./
RUN chmod +x mvnw || true

# Copy the pom.xml
COPY pom.xml .

# Download dependencies as a separate step to take advantage of Docker's caching.
RUN ./mvnw dependency:go-offline -DskipTests || mvn dependency:go-offline -DskipTests

################################################################################

# Create a stage for building the application.
FROM deps as package

WORKDIR /build

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

################################################################################

# Create the final stage for running the application.
FROM eclipse-temurin:17-jre-jammy as final

WORKDIR /app

# Copy the packaged JAR file directly into the final image
COPY --from=package /build/target/*.jar app.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

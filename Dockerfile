# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy only the necessary files for dependency download
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# No need to copy settings.xml

# Download the dependencies
RUN mvn dependency:go-offline

# Copy the entire project
COPY . .

# Build the application
RUN mvn package -DskipTests

# Stage 2: Build the final image
FROM openjdk:17

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]

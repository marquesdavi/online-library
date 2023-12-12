FROM ubuntu:latest
LABEL authors="marquesdavi"

WORKDIR /app
ENTRYPOINT ["top", "-b"]

# Stage 1: Build Frontend
FROM node:16.17.1-slim AS build-node
WORKDIR /app
COPY package.json .
COPY package-lock.json .
ARG NODE_ENV=production
ENV NODE_ENV=${NODE_ENV}
RUN npm install
RUN npm install -g nodemon
COPY . .
RUN if [ "$NODE_ENV" = "development" ]; then npm run build:development; else npm run build:production; fi
COPY wait-for-it.sh .

# Stage 2: Build Backend
FROM openjdk:17-alpine AS build-backend
WORKDIR /app
COPY pom.xml .
COPY src src
COPY --from=build-node /app/target/classes/static /app/src/main/resources/static

# Install Maven
RUN apk add --no-cache maven

# Run Maven package command
RUN mvn package -DskipTests -P dev -q

# Stage 3: Production Image
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build-backend /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]

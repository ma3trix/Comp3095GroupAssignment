#--------------------------------
# Build stage
#--------------------------------

# Use a Gradle base image for building
FROM gradle:8-jdk17 AS builder

# Set the working directory within the image
WORKDIR /home/gradle/src

# Copy the Gradle build files and sources
COPY --chown=gradle:gradle . .

# Build the application (skip tests)
RUN gradle build -x test

#---------------------------------
# Package Stage
#------------------------------------

# Use a smaller runtime image
FROM openjdk:17-alpine

# Create a directory for the application
RUN mkdir /app

# Copy the built JAR file from the builder stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/friendship-service.jar

# Set environment variables if needed
ENV MONGO_DB_USERNAME=mongoadmin
ENV MONGO_DB_PASSWORD=password

# Expose the port on which the application will run
EXPOSE 8087

# Specify the entry point to start the application
ENTRYPOINT ["java", "-jar", "/app/friendship-service.jar"]

#--------------------------------
# Dockerfile for order-service
#--------------------------------

#--------------------
#Build stage
#--------------------

# base image
FROM gradle:8-jdk17-alpine AS builder

# Copy the application files from the host machine to the image filesystem
COPY --chown=gradle:gradle . /home/gradle/src

# set the directory within the image, for executing future commands
WORKDIR /home/gradle/src

# run gradle build within the container - NOT the host machine
# for RUN commands, the command only apply to images
# RUN gradle build ---no-daemon -x test -- this did NOT WORK
RUN gradle build -x test

#---------------------------------
#---Package Stage
#------------------------------------

# Base Image
FROM openjdk:17-alpine

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/api-gateway.jar


#Run products servers on this port
EXPOSE 8181

ENTRYPOINT ["java","-jar","/app/api-gateway.jar"]
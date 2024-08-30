#
# Build stage
#
FROM maven:3.9.5-eclipse-temurin-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM eclipse-temurin:21-jdk-alpine

COPY --from=build /home/app/target/*.jar app.jar

EXPOSE 8080

#
# Set time zone
#
RUN apk update && apk add --no-cache tzdata 
ENV TZ=America/Bogota

#
# Execute app
CMD ["java", "-jar", "app.jar"]

FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/whereflix-0.0.1-SNAPSHOT.jar app.jar
COPY upload upload
RUN ls -l upload

ENTRYPOINT ["java", "-jar", "app.jar"]
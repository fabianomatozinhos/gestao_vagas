FROM ubuntu:latest as build
LABEL authors="fabiano_matozinhos"

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

WORKDIR /app

COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine

EXPOSE 8080

COPY --from=build /app/target/gestao_vagas-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]
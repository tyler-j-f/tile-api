#
# Build stage
#
FROM maven:3.8.3-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -P remote

#
# Package stage
#
FROM openjdk:18-ea-17-slim
COPY --from=build /home/app/target/tile-api-app:v1.0.2 /usr/local/lib/demo_api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo_api.jar"]
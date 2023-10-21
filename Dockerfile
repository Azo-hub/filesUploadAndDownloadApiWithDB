
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:17-alpine
COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
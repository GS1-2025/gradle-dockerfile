FROM gradle:jdk21-graal AS build
WORKDIR /usr/app/
COPY . .

RUN gradle build -x test

FROM openjdk:21-jdk-slim
WORKDIR /usr/app/
COPY --from=build /usr/app .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/api-security-1.0.0.jar"]

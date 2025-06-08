FROM gradle:8.13-jdk21 AS build
WORKDIR /usr/app/
COPY . .

RUN gradle build -x test

FROM gradle:8.13-jdk21
WORKDIR /usr/app/
COPY --from=build /usr/app .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/Sensolux-1.0.0.jar"]


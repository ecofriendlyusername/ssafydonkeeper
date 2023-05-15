FROM adoptopenjdk/openjdk11 AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM adoptopenjdk/openjdk11
COPY --from=builder build/libs/*.jar app.jar
COPY src/main/resources/application.yml /config/application.yml
COPY src/main/resources/application.yml /config/application.yml
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

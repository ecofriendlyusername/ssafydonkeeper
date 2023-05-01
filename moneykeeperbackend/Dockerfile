FROM openjdk:11 AS builder

# Gradle install
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-7.6.1-bin.zip && \
    unzip -d /opt gradle-7.6.1-bin.zip && \
    rm gradle-7.6.1-bin.zip

# Gradle configuration
ENV GRADLE_HOME /opt/gradle-7.6.1
ENV PATH $GRADLE_HOME/bin:$PATH

# app build
WORKDIR /app
COPY . .
RUN gradle bootJar
COPY src/main/resources/application.yml src/main/resources/

FROM openjdk:11
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]

FROM openjdk:11 AS builder

# Gradle 설치하기
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-7.6.1-bin.zip && \
    unzip -d /opt gradle-7.6.1-bin.zip && \
    rm gradle-7.6.1-bin.zip

# Gradle 환경 변수 설정하기
ENV GRADLE_HOME /opt/gradle-7.6.1
ENV PATH $GRADLE_HOME/bin:$PATH

# 앱 빌드하기
WORKDIR /app
COPY . .
RUN gradle bootJar
COPY src/main/resources/application.yml src/main/resources/

FROM openjdk:11
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
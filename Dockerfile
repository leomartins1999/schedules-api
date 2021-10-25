FROM gradle:7.2.0-jdk11

WORKDIR /app

COPY gradle gradle
COPY gradlew gradlew
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
COPY src src

RUN ["gradle", "build", "-x", "test", "-x", "check"]

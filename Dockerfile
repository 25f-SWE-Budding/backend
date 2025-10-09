# ---- builder ----
FROM gradle:8.10-jdk17 AS builder
WORKDIR /app
COPY gradlew ./
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
# 의존성 캐시 레이어
RUN ./gradlew --no-daemon dependencies || true
# 소스는 나중에
COPY src src
# 배포 빌드: 테스트 제외
RUN ./gradlew --no-daemon clean bootJar -x test

# ---- runtime ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport"
# Spring Boot는 기본 8080 사용(필요시 SERVER_PORT env로 바꾸면 됨)
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

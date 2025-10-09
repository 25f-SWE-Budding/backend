# ---- builder ----
FROM gradle:8.14.3-jdk21 AS builder
WORKDIR /app

COPY gradlew ./
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew

# 1) Gradle wrapper 동작 확인
RUN ./gradlew --version

# 2) 의존성 레이어 캐시 (실패해도 계속 진행)
RUN ./gradlew --no-daemon dependencies --stacktrace --info || true

# 3) 소스 복사
COPY src src

# 4) 진짜 빌드 (여기에 자세한 로그 옵션 추가)
RUN ./gradlew --no-daemon clean bootJar -x test --stacktrace --info

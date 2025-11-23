# ==========================
# 1) Build Stage - Gradle로 JAR 빌드
# ==========================
FROM gradle:8.10.2-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 캐시 최적화를 위해 build.gradle, settings.gradle 먼저 복사
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle

# gradlew 파일 복사 후 실행 권한 부여 (Windows/Linux 호환)
COPY gradlew /app/
RUN chmod +x gradlew

# 프로젝트 전체 소스코드 복사
COPY . /app/

# 테스트를 제외하고 JAR 빌드 (가장 안정적)
RUN gradle clean bootJar -x test


# ==========================
# 2) Run Stage - 경량 JDK 이미지로 실행
# ==========================
FROM eclipse-temurin:17-jre

# 컨테이너 작업 디렉토리
WORKDIR /app

# Build Stage에서 생성된 jar 복사
COPY --from=build /app/build/libs/librarymanagementsystem-0.0.1-SNAPSHOT.jar /app/app.jar

# 외부에 노출할 포트
EXPOSE 8080

# Spring Boot 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
# Build stage
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /build
COPY . .
RUN ./gradlew build --no-daemon

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /build/build/libs/Demodeploycicd-0.0.1-SNAPSHOT.jar app.jar

# Thêm tối ưu: Sử dụng non-root user cho bảo mật
RUN addgroup --system javauser && adduser --system --ingroup javauser javauser
USER javauser

EXPOSE 8080
# Tối ưu JVM options để giảm bộ nhớ sử dụng
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
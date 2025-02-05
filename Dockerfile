# Maven ve Java 21 içeren base image
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build

# Çalışma dizini oluştur
WORKDIR /app

# Pom.xml ve src klasörünü kopyala
COPY pom.xml .
COPY src ./src

# Maven ile build al
RUN mvn clean package -DskipTests

# Runtime image
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Build aşamasında oluşturulan JAR dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

# Uygulamanın çalışacağı portu belirt
EXPOSE 8080

# Container başlatıldığında çalıştırılacak komut
ENTRYPOINT ["java","-jar","app.jar"] 
# Etapa 1: Build da aplicação com Maven (usando imagem com Maven)
FROM eclipse-temurin:23-jdk AS builder
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Etapa 2: Executa o JAR
FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
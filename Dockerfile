# Etapa de build
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar o arquivo pom.xml e resolver dependências
COPY pom.xml .
RUN mvn dependency:resolve

# Copiar os arquivos do projeto e realizar o build
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar o artefato gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Configurar o comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]

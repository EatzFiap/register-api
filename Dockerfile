# Etapa 1 - Build da aplicação
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY --chown=gradle:gradle . /app

RUN gradle build -x test --no-daemon

# Etapa 2 - Imagem final da aplicação somente com o jar
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

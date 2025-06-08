FROM gradle:8.13-jdk21 AS build

WORKDIR /usr/app/
COPY . .
RUN gradle build -x test

# Etapa final
FROM eclipse-temurin:21-jdk

# Cria um usuário e grupo não root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Define diretório de trabalho e copia os arquivos da etapa de build
WORKDIR /usr/app/
COPY --from=build /usr/app . 

# Ajusta permissões
RUN chown -R appuser:appgroup /usr/app

# Muda para o usuário não root
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/Sensolux-1.0.0.jar"]

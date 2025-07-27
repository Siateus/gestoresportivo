# Dockerfile otimizado para build cache
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o Maven Wrapper e o POM primeiro para cachear dependências
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Baixa as dependências do Maven (essa camada será recriada apenas se o pom.xml mudar)
RUN ./mvnw dependency:go-offline

# Copia o restante do código-fonte (essa camada será recriada em cada mudança de código)
COPY src ./src

# Compila o projeto



# build
FROM maven:3.8.8-openjdk-21 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# run
FROM openjdk:21
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8081

# Variáveis de ambiente
ENV DATASOURCE_URL=""
ENV DATASOURCE_USERNAME=""
ENV DATASOURCE_PASSWORD=""

ENV MAIL_HOST=""
ENV MAIL_PORT=""
ENV MAIL_USERNAME=""
ENV MAIL_PASSWORD=""
ENV MAIL_SMTP=""

ENV SPRING_PROFILES_ACTIVE="production"
ENV TZ="America/Sao_Paulo"

ENTRYPOINT ["java", "-jar", "app.jar"]

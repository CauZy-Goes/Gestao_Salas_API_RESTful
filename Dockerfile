FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8081

ENV DATASOURCE_URL=""
ENV DATASOURCE_USERNAME=""
ENV DATASOURCE_PASSWORD=""

ENV MAIL_HOST=""
ENV MAIL_PORT=""
ENV MAIL_USERNAME=""
ENV MAIL_PASSWORD=""
ENV MAIL_SMTP=""

ENV OPENAI_API_KEY=""
ENV TWILIO_ACCOUNT_SID=""
ENV TWILIO_AUTH_TOKEN=""
ENV TWILIO_PHONE_NUMBER=""

ENV SPRING_PROFILES_ACTIVE="production"
ENV TZ="America/Sao_Paulo"

ENTRYPOINT ["java", "-jar", "app.jar"]

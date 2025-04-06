# build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# run
FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8081

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''

ENV MAIL_HOST=''
ENV MAIL_PORT=''
ENV MAIL_USERNAME=''
ENV MAIL_PASSWORD=''
ENV MAIL_SMTP=''

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar app.jar

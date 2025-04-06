
# 🚀 API RESTful - Gestão de Espaços Físicos 🏫

Esta API RESTful foi desenvolvida para gerenciar solicitações de uso de espaços físicos em instituições, permitindo que professores realizem pedidos e gestores façam avaliações com controle total. Construída com Java 21 e Spring Boot 3, a aplicação implementa autenticação e autorização com OAuth2 e JWT, documentação interativa com Swagger, paginação, filtros dinâmicos com Specifications e ExampleMatcher, validações robustas com Spring Validator, envio automático de e-mails, monitoramento via Actuator, controle de acesso com @PreAuthorize, tratamento global de exceções, log estruturado com SLF4J e deploy automatizado com Docker — tudo seguindo os princípios do Clean Code e SOLID para garantir escalabilidade, clareza e segurança.

---

## 🔍 Visão Geral
Este projeto é uma API RESTful poderosa, robusta e extensível, desenvolvida para gerenciar de forma eficiente as solicitações de uso de espaços físicos em instituições de ensino ou ambientes corporativos. Ele permite que professores registrem solicitações de reserva e que gestores (avaliadores) as aprovem ou rejeitem com base na disponibilidade.

Após diversas melhorias e refatorações, a aplicação atingiu um novo patamar de qualidade e desempenho, passando a adotar uma arquitetura moderna e altamente escalável. Dentre os principais aprimoramentos, destacam-se:

✅ Princípios do Clean Code e SOLID aplicados em todo o projeto. <br/>
🔐 Segurança reforçada com Spring Security, autenticação com JWT e suporte completo a OAuth2 (Authorization Code Flow e Client Credentials).<br/>
🛡️ Controle de acesso com anotações como @PreAuthorize("hasRole('GESTOR')") para proteger endpoints sensíveis.<br/>
🔍 Documentação interativa com Swagger/OpenAPI, permitindo testar os endpoints diretamente na interface.<br/>
🩺 Monitoramento e métricas com Spring Boot Actuator, expondo informações detalhadas sobre o sistema.<br/>
❌ Tratamento global de exceções com resposta padronizada para erros de negócio, validações e falhas inesperadas.<br/>
📩 Envio de e-mails automático em eventos importantes, como alteração de status de solicitações.<br/>
🧾 Sistema avançado de logs com @Slf4j e configuração de logging com rotação e persistência.<br/>
⚙️ Validações avançadas usando Spring Validator e anotações customizadas.<br/>
🔎 Filtros dinâmicos com Specifications e ExampleMatcher para buscas flexíveis e paginadas.<br/>
🧰 Integração com Thymeleaf para renderização de views personalizadas (como telas de login).<br/>
🐳 Containerização com Docker, incluindo variáveis de ambiente para fácil configuração e deploy em produção.<br/>
🔁 Separação em camadas bem definidas, com uso de DTOs, mapeadores via MapStruct e testes facilitados.<br/>

Esta API está preparada para ser utilizada em ambientes de produção, com alto nível de segurança, manutenibilidade e extensibilidade para novos recursos.

## 🌐 Estrutura de Pacotes - Spring Web (REST)

```
rest/
├── configuration/     # Configurações específicas para o módulo web (ex: CORS, interceptadores)
├── controller/        # Endpoints da API - comunicação com o cliente
├── dto/               # Objetos de transferência de dados (entrada e saída da API)
├── exception/         # Tratamento de exceções específicas da camada REST
├── mapper/            # Conversão entre entidades e DTOs (ex: com MapStruct)
├── validator/         # Validações customizadas e anotações personalizadas
```

---

## 🗃️ Estrutura de Pacotes - Spring Data JPA (Domain)

```
domain/
├── entity/            # Entidades JPA que representam as tabelas do banco de dados
├── repository/        # Interfaces de repositório com Spring Data JPA
├── service/           # Lógica de negócio e regras de domínio
├── utils.exception/   # Exceções reutilizáveis aplicadas na lógica de domínio
```

---

## 📦 Estrutura de Pacotes Spring Security

```
security/
├── auth/                      # Filtros, tokens e providers personalizados
├── config/                    # Configurações do Authorization Server e WebSecurity
├── client/                    # Registro dinâmico de clients
├── service/                   # Integração com UserDetailsService
```

---

## 📂 Entidades do Sistema

- 📌 **Status**
- 📌 **Espaço Físico**
- 📌 **Cargos**
- 📌 **Usuários** (Professor, Gestor)
- 📌 **Tipo de Sala**
- 📌 **Solicitações**
---

## ⚙️ Tecnologias Utilizadas

| Tecnologia                        | Função                                                       |
|----------------------------------|--------------------------------------------------------------|
| **Java 21**                      | Linguagem base                                               |
| **Spring Boot 3**                | Core do framework                                            |
| **Spring Security**              | Autenticação e Autorização                                   |
| **Spring Authorization Server**  | OAuth2 + JWT                                                 |
| **Spring Data JPA / Hibernate**  | ORM para persistência                                        |
| **PostgreSQL**                   | Banco de dados                                               |
| **MapStruct**                    | Conversão entre DTOs e Entidades                             |
| **Spring Mail**                  | Envio de e-mails                                             |
| **Bean Validation / Custom Validator** | Validações robustas com mensagens customizadas     |
| **Swagger / Springdoc OpenAPI**  | Documentação interativa da API                               |
| **Spring Boot Actuator**         | Monitoramento da aplicação                                   |
| **Docker**                       | Containerização e deploy                                     |
| **Thymeleaf**                    | Autenticação via formulário OAuth2                           |
| **Slf4j + logback**              | Sistema de logs                                              |

---

## 🐳 Docker

### Dockerfile

```dockerfile
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

```

## 🔐 Segurança com OAuth2 e JWT

-  **Fluxos suportados**:
  - **Client Credentials**
  - **Authorization Code com Login via Thymeleaf**
-  **Tokens JWT Assinados**
-  **Autorização com @PreAuthorize**
-  **Filtro personalizado de autenticação de client**

---

## 📈 Observabilidade

-  **Spring Boot Actuator**
-  Endpoints: `/actuator/health`, `/actuator/metrics`, `/actuator/loggers`, `/actuator/heapdump` etc.
-  Sistema de log rotativo configurado via `logback-spring.xml`

![Actuator](https://github.com/CauZy-Goes/Gestao_Solicitacoes_Espacos_API/blob/main/imgs%20api%20gestao%20salas/ACTUATOR.png?raw=true)

---

## 📄 Documentação Interativa com Swagger

Após rodar a aplicação, acesse:

```
http://localhost:8081/swagger-ui/index.html
```

![Swagger](https://github.com/CauZy-Goes/Gestao_Solicitacoes_Espacos_API/blob/main/imgs%20api%20gestao%20salas/SWAGGER.png?raw=true)

---

## 🛠️ Como Rodar Localmente

```bash

# Construa a imagem Docker
docker build -t gestaoespacos .

# Execute o container com as variáveis de ambiente necessárias
docker run -p 8081:8081 \
  -e DATASOURCE_URL="jdbc:postgresql://localhost:5432/gestao" \
  -e DATASOURCE_USERNAME="postgres" \
  -e DATASOURCE_PASSWORD="sua_senha" \
  -e MAIL_HOST="smtp.seudominio.com" \
  -e MAIL_PORT="587" \
  -e MAIL_USERNAME="email@seudominio.com" \
  -e MAIL_PASSWORD="senha_email" \
  -e MAIL_SMTP="smtp.seudominio.com" \
  -e SPRING_PROFILES_ACTIVE="production" \
  -e TZ="America/Sao_Paulo" \
  gestaoespacos
```

---

## 🧠 Contribuições e Boas Práticas Adotadas

-  **Princípios SOLID**
-  **Camadas bem definidas**
-  **Boas práticas de DTOs, validações, tratamento de erros e organização de pacotes**
-  **Autenticação e segurança alinhada com padrões modernos**

---

## 👨‍💻 Autor

Desenvolvido por [Cauã Farias (CauZy-Goes)](https://github.com/CauZy-Goes)  
---

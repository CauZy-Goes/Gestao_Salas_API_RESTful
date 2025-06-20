
# 🚀 API RESTful - Gestão de Espaços Físicos 🏫

Esta API RESTful foi desenvolvida para gerenciar solicitações de uso de espaços físicos em instituições, permitindo que professores realizem pedidos e gestores façam avaliações com controle total. Construída com Java 21 e Spring Boot 3, a aplicação implementa autenticação e autorização com OAuth2 e JWT, documentação interativa com Swagger, paginação, filtros dinâmicos com Specifications e ExampleMatcher, validações robustas com Spring Validator, envio automático de e-mails, monitoramento via Actuator, controle de acesso com @PreAuthorize, tratamento global de exceções, log estruturado com SLF4J, integração com chatbot via WhatsApp (usando Twilio e OpenAI) e deploy automatizado com Docker — tudo seguindo os princípios do Clean Code e SOLID para garantir escalabilidade, clareza e segurança.

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
💬 Integração com um Chatbot no WhatsApp utilizando OpenAI (GPT) e Twilio, permitindo interações inteligentes para criar, listar solicitações e consultar espaços físicos diretamente pelo WhatsApp.<br/>
🧾 Sistema avançado de logs com @Slf4j e configuração de logging com rotação e persistência.<br/>
⚙️ Validações avançadas usando Spring Validator e anotações customizadas.<br/>
🔎 Filtros dinâmicos com Specifications e ExampleMatcher para buscas flexíveis e paginadas.<br/>
🧰 Integração com Thymeleaf para renderização de views personalizadas (como telas de login).<br/>
🐳 Containerização com Docker, incluindo variáveis de ambiente para fácil configuração e deploy em produção.<br/>
🔁 Separação em camadas bem definidas, com uso de DTOs, mapeadores via MapStruct e testes facilitados.<br/>

Esta API está preparada para ser utilizada em ambientes de produção, com alto nível de segurança, manutenibilidade e extensibilidade para novos recursos.

## 🤖 Funcionalidade do Chatbot no WhatsApp

O sistema conta com um chatbot inteligente, desenvolvido com integração ao **OpenAI (GPT-3.5)** e **Twilio**, que permite aos professores interagirem diretamente com a api pelo WhatsApp para:

- 📋 Listar todas as solicitações de um professor
- 🏫 Listar todos os espaços físicos disponíveis
- ✍️ Criar novas solicitações de uso de espaços físicos
- 💬 Bater papo e interagir naturalmente com os usuários

### 🔗 Funcionamento:

- A comunicação é feita por meio de um webhook `/webhook` que recebe as mensagens do WhatsApp via Twilio.
- A IA interpreta as mensagens, entende a intenção e responde com ações como listar, cadastrar ou consultar espaços.
- Toda a lógica de integração está no serviço `ChatBotService`.

### 🔑 Tecnologias usadas no chatbot:

- **Twilio API for WhatsApp**
- **OpenAI GPT-3.5 Turbo**
- **Spring Boot 3**
- **Webhook RESTful**

---

### 🖼️ Imagens do Chatbot em Ação

| ![1](imgs%20api%20gestao%20salas/chat_bot_apresentacao.png) | ![2](imgs%20api%20gestao%20salas/chat_bot_salas.png) |
|-------------------------|-------------------------|
| ![3](imgs%20api%20gestao%20salas/chat_bot_soli1.png) | ![4](imgs%20api%20gestao%20salas/chat_bot_salas_soli2.png) |

---

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
| **Twilio API**                   | Integração com WhatsApp                                      |
| **OpenAI GPT API**               | Inteligência Artificial para o Chatbot                       |

---

## 🐳 Docker

### Dockerfile

```dockerfile
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
docker run -d -p 8081:8081 \
  -e DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/gestao" \
  -e DATASOURCE_USERNAME="postgres" \
  -e DATASOURCE_PASSWORD="suaSenha" \
  -e MAIL_HOST="smtp.gmail.com" \
  -e MAIL_PORT="587" \
  -e MAIL_USERNAME="seuemail@gmail.com" \
  -e MAIL_PASSWORD="suaSenhaEmail" \
  -e MAIL_SMTP="smtp.gmail.com" \
  -e OPENAI_API_KEY="sk-xxxxxxxxxxxxxxxxxxxx" \
  -e TWILIO_ACCOUNT_SID="ACxxxxxxxxxxxxxxxx" \
  -e TWILIO_AUTH_TOKEN="xxxxxxxxxxxxxxxxxxxx" \
  -e TWILIO_PHONE_NUMBER="whatsapp:+55XXXXXXXXXXX" \
  --name gestao-espacos \
  gestao-espacos-api
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

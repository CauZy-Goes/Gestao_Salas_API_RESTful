# 🚀 API - Gestão de Solicitações para Uso de Espaços Físicos 🏫

Este projeto é uma API REST desenvolvida para gerenciar solicitações de uso de espaços físicos em uma instituição, garantindo organização e eficiência no agendamento de salas. Professores podem solicitar reservas, enquanto avaliadores podem aprovar ou rejeitar os pedidos.

A estrutura do sistema é robusta, utilizando um banco de dados relacional com diversas relações entre entidades para garantir uma gestão eficiente das informações.

## 🔥 Principais Funcionalidades REST

- ✔️ **Cadastro de Solicitações**: Professores podem registrar pedidos informando data, horário e finalidade.
- ✔️ **Gerenciamento de Usuários**: Cadastro de professores (solicitantes) e avaliadores (responsáveis pela aprovação).
- ✔️ **Aprovação de Solicitações**: Avaliadores podem aprovar ou rejeitar pedidos com base na disponibilidade.
- ✔️ **Histórico de Solicitações**: Registro completo de todas as requisições e suas mudanças de status.
- ✔️ **CRUD Completo**: Todas as entidades possuem suporte para operações Create, Read, Update e Delete (CRUD) via APIs RESTful.
- ✔️ **MapStruct**: Utilizado para conversão eficiente entre DTOs e entidades, otimizando a performance do sistema.
- ✔️ **Envio de E-mails**: Agora, quando um gestor altera uma solicitação (aceita ou rejeita), um e-mail é enviado automaticamente para o professor solicitante.

## 📂 Entidades do Sistema

O banco de dados foi projetado para lidar com um grande volume de dados e relações. As principais tabelas incluem:

- 📌 **Status**: Gerencia os diferentes estados das solicitações.
- 📌 **Espaço Físico**: Armazena informações sobre as salas disponíveis para reserva.
- 📌 **Equipamentos**: Lista os equipamentos disponíveis nos espaços físicos.
- 📌 **Cargos**: Define os cargos dos usuários cadastrados no sistema.
- 📌 **Usuários**: Professores (solicitantes) e avaliadores.
- 📌 **Tipo de Sala**: Classifica as salas conforme suas características.
- 📌 **Solicitações**: Registra os pedidos feitos pelos usuários.
- 📌 **Log de Ações**: Mantém um histórico detalhado de todas as ações realizadas no sistema.

## 💾 Tecnologias Utilizadas

- ✅ **Java 17** – Linguagem principal do projeto.
- ✅ **Spring Boot 3** – Desenvolvimento do back-end e criação das APIs RESTful.
- ✅ **PostgreSQL** – Banco de dados relacional para persistência de informações.
- ✅ **Hibernate** – ORM para manipulação eficiente das entidades.
- ✅ **MapStruct** – Conversão automática entre entidades e DTOs.
- ✅ **Spring Boot Starter Mail** – Para envio de e-mails quando um gestor altera uma solicitação.
- ✅ **Maven** – Gerenciador de dependências e build do projeto.

## 🔗 Endpoints da API

### 🏢 Cargos
```
GET /api/cargos - Lista todos os cargos
GET /api/cargos/{id} - Retorna um cargo por ID
POST /api/cargos - Cria um novo cargo
PUT /api/cargos/{id} - Atualiza um cargo existente
DELETE /api/cargos/{id} - Exclui um cargo
```

### 🛠️ Equipamentos
```
GET /api/equipamentos - Lista todos os equipamentos
GET /api/equipamentos/{id} - Retorna um equipamento por ID
POST /api/equipamentos - Cria um novo equipamento
PUT /api/equipamentos/{id} - Atualiza um equipamento existente
DELETE /api/equipamentos/{id} - Exclui um equipamento
```

### 🏫 Espaços Físicos
```
GET /api/espacoFisicos - Lista todos os espaços físicos
GET /api/espacoFisicos/{id} - Retorna um espaço físico por ID
POST /api/espacoFisicos - Cria um novo espaço físico
PUT /api/espacoFisicos/{id} - Atualiza um espaço físico existente
DELETE /api/espacoFisicos/{id} - Exclui um espaço físico
```

### 📜 Log de Ações
```
GET /api/logAcoes - Lista todos os logs de ações
GET /api/logAcoes/{id} - Retorna um log de ações por ID
POST /api/logAcoes - Cria um novo log de ações
PUT /api/logAcoes/{id} - Atualiza um log de ações existente
DELETE /api/logAcoes/{id} - Exclui um log de ações
```

### 📌 Solicitações
```
GET /api/solicitacoes - Lista todas as solicitações
GET /api/solicitacoes/{id} - Retorna uma solicitação por ID
POST /api/solicitacoes - Cria uma nova solicitação
PUT /api/solicitacoes/{id} - Atualiza uma solicitação existente
DELETE /api/solicitacoes/{id} - Exclui uma solicitação
```

## 🛠️ Configuração do Ambiente

### 1️⃣ Clone o repositório:
```sh
git clone https://github.com/CauZy-Goes/Gestao_Solicitacoes_Espacos_REST.git
cd Gestao_Solicitacoes_Espacos_REST
```

### 2️⃣ Configure o banco de dados:
Certifique-se de ter um banco PostgreSQL configurado com as credenciais adequadas no `application.properties`.

### 3️⃣ Configure o envio de e-mails:
Adicione as configurações de seu provedor de e-mail no `application.properties`:
```properties
spring.mail.host=smtp.exemplo.com
spring.mail.port=587
spring.mail.username=seu-email@exemplo.com
spring.mail.password=sua-senha
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 4️⃣ Execute a API:
```sh
mvn spring-boot:run
```

## 🔗 Repositório do Sistema Web

O front-end deste projeto foi desenvolvido com JSF e PrimeFaces, consumindo essa API REST. Para conferir a aplicação em ação, acesse o repositório:

🔹 **Frontend Web: Gestão de Solicitações de Salas**: [Clique aqui](https://github.com/CauZy-Goes/Gestao_E_Solicitao_De_Salas)

## 🎯 Contribuição

Sinta-se à vontade para contribuir! Basta fazer um fork do repositório, criar uma nova branch com sua funcionalidade ou correção e abrir um pull request. 🚀

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

---

Feito por **Cauã Farias** 🚀

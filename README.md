# 🚀 Gestão de Solicitações para Uso de Espaços Físicos 🏫

Este projeto é uma **API REST** desenvolvida para gerenciar solicitações de uso de espaços físicos em uma instituição, garantindo organização e eficiência no agendamento de salas. **Professores** podem solicitar reservas, enquanto **avaliadores** podem aprovar ou rejeitar os pedidos.

A estrutura do sistema é robusta, utilizando um **banco de dados relacional** com diversas relações entre entidades para garantir uma gestão eficiente das informações.

## 🔥 Principais Funcionalidades REST

✔️ **Cadastro de Solicitações**: Professores podem registrar pedidos informando data, horário e finalidade.<br/>
✔️ **Gerenciamento de Usuários**: Cadastro de professores (solicitantes) e avaliadores (responsáveis pela aprovação).<br/>
✔️ **Aprovação de Solicitações**: Avaliadores podem aprovar ou rejeitar pedidos com base na disponibilidade. <br/>
✔️ **Histórico de Solicitações**: Registro completo de todas as requisições e suas mudanças de status.<br/>
✔️ **CRUD Completo**: Todas as entidades possuem suporte para operações **Create, Read, Update e Delete (CRUD)** via APIs RESTful.<br/>
✔️ **MapStruct**: Utilizado para conversão eficiente entre **DTOs e entidades**, otimizando a performance do sistema.<br/>

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

✅ **Java 17** – Linguagem principal do projeto.<br/>
✅ **Spring Boot 3** – Desenvolvimento do back-end e criação das APIs RESTful.<br/>
✅ **PostgreSQL** – Banco de dados relacional para persistência de informações.<br/>
✅ **Hibernate** – ORM para manipulação eficiente das entidades.<br/>
✅ **MapStruct** – Conversão automática entre entidades e DTOs.<br/>
✅ **Maven** – Gerenciador de dependências e build do projeto.<br/>

## 🔗 Endpoints da API

### 🏢 Cargos
- **GET /api/cargos** - Lista todos os cargos
- **GET /api/cargos/{id}** - Retorna um cargo por ID
- **POST /api/cargos** - Cria um novo cargo
- **PUT /api/cargos/{id}** - Atualiza um cargo existente
- **DELETE /api/cargos/{id}** - Exclui um cargo

### 🛠️ Equipamentos
- **GET /api/equipamentos** - Lista todos os equipamentos
- **GET /api/equipamentos/{id}** - Retorna um equipamento por ID
- **POST /api/equipamentos** - Cria um novo equipamento
- **PUT /api/equipamentos/{id}** - Atualiza um equipamento existente
- **DELETE /api/equipamentos/{id}** - Exclui um equipamento

### 🏫 Espaços Físicos
- **GET /api/espacoFisicos** - Lista todos os espaços físicos
- **GET /api/espacoFisicos/{id}** - Retorna um espaço físico por ID
- **POST /api/espacoFisicos** - Cria um novo espaço físico
- **PUT /api/espacoFisicos/{id}** - Atualiza um espaço físico existente
- **DELETE /api/espacoFisicos/{id}** - Exclui um espaço físico

### 📜 Log de Ações
- **GET /api/logAcoes** - Lista todos os logs de ações
- **GET /api/logAcoes/{id}** - Retorna um log de ações por ID
- **POST /api/logAcoes** - Cria um novo log de ações
- **PUT /api/logAcoes/{id}** - Atualiza um log de ações existente
- **DELETE /api/logAcoes/{id}** - Exclui um log de ações

### 📌 Solicitações
- **GET /api/solicitacoes** - Lista todas as solicitações
- **GET /api/solicitacoes/{id}** - Retorna uma solicitação por ID
- **POST /api/solicitacoes** - Cria uma nova solicitação
- **PUT /api/solicitacoes/{id}** - Atualiza uma solicitação existente
- **DELETE /api/solicitacoes/{id}** - Exclui uma solicitação

### 📊 Status
- **GET /api/status** - Lista todos os status
- **GET /api/status/{id}** - Retorna um status por ID
- **POST /api/status** - Cria um novo status
- **PUT /api/status/{id}** - Atualiza um status existente
- **DELETE /api/status/{id}** - Exclui um status

### 🏢 Tipos de Sala
- **GET /api/tipoSalas** - Lista todos os tipos de salas
- **GET /api/tipoSalas/{id}** - Retorna um tipo de sala por ID
- **POST /api/tipoSalas** - Cria um novo tipo de sala
- **PUT /api/tipoSalas/{id}** - Atualiza um tipo de sala existente
- **DELETE /api/tipoSalas/{id}** - Exclui um tipo de sala

### 👥 Usuários
- **GET /api/usuarios** - Lista todos os usuários
- **GET /api/usuarios/{id}** - Retorna um usuário por ID
- **GET /api/usuarios/email/{email}** - Retorna um usuário por email
- **POST /api/usuarios** - Cria um novo usuário
- **PUT /api/usuarios/{id}** - Atualiza um usuário existente
- **DELETE /api/usuarios/{id}** - Exclui um usuário

## 🛠️ Configuração do Ambiente

1️⃣ **Clone o repositório:**

```sh
git clone https://github.com/CauZy-Goes/Gestao_Solicitacoes_Espacos_REST.git
cd Gestao_Solicitacoes_Espacos_REST
```

2️⃣ **Configure o banco de dados:**

Certifique-se de ter um banco **PostgreSQL** configurado com as credenciais adequadas no `application.properties`.

3️⃣ **Execute a API:**

```sh
mvn spring-boot:run
```

## 🔗 Repositório do Sistema Web

O front-end deste projeto foi desenvolvido com **JSF e PrimeFaces**, consumindo essa API REST. Para conferir a aplicação em ação, acesse o repositório:

🔹 **Frontend Web**: Gestão de Solicitações de Salas : https://github.com/CauZy-Goes/Gestao_E_Solicitao_De_Salas

🚀 Este projeto foi essencial para expandir meu conhecimento em **back-end e API REST**, me desafiando a criar uma aplicação **robusta e escalável**! Feedbacks são sempre bem-vindos! 😊

## 🎯 Contribuição

Sinta-se à vontade para contribuir! Basta fazer um **fork** do repositório, criar uma nova **branch** com sua funcionalidade ou correção e abrir um **pull request**. 🚀

## 📝 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo `LICENSE` para mais detalhes.

---

Feito por **Cauã Farias** 🚀



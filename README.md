# API de Agência de Viagens

Este projeto consiste em uma API RESTful desenvolvida com **Java** e **Spring Boot**, criada para gerenciar destinos de viagens. A API permite operações **CRUD** (Create, Read, Update, Delete) básicas em destinos, além de funcionalidades de **pesquisa** e **avaliação**. Também inclui integração com banco de dados **PostgreSQL** e um sistema completo de segurança com **Spring Security** para autenticação e autorização de usuários.

Este projeto foi desenvolvido como parte de um desafio de desenvolvimento de aplicações, focando em persistência de dados e segurança.


## Funcionalidades

### Gerenciamento de Destinos

- **Cadastrar Destino:** Permite inserir um novo destino de viagem.  
  * `POST /api/destinations`

- **Listar Destinos:** Retorna a lista de todos os destinos disponíveis.  
  * `GET /api/destinations`

- **Pesquisar Destinos:** Permite pesquisar destinos por nome ou localização.  
  * `GET /api/destinations/search?query={termo}`

- **Visualizar Detalhes do Destino:** Retorna informações detalhadas sobre um destino específico por ID.  
  * `GET /api/destinations/{id}`

- **Avaliar Destino:** Permite atribuir uma nota (1-10) a um destino, atualizando sua média.  
  * `PATCH /api/destinations/{id}/rate?rating={nota}`

- **Atualizar Destino:** Atualiza todas as informações de um destino existente por ID.  
  * `PUT /api/destinations/{id}`

- **Excluir Destino:** emove um destino específico por ID.  
  * `DELETE /api/destinations/{id}`

# Autenticação e Autorização

## Registrar Usuário
Permite que novos usuários se cadastrem com um nome de usuário, senha e papel (ex: `ROLE_USER`, `ROLE_ADMIN`).

**Endpoint:**  
* `POST /api/auth/register`  
**Acesso:** `ROLE_ADMIN` (Usuário autenticado com perfil de Administrador)

## Fazer Login
Autentica um usuário existente.

**Endpoint:**  
* `POST /api/auth/login`  
**Acesso:** Público

## Proteção de Endpoints
A maioria dos endpoints (`/api/destinations/**`) agora exige autenticação via **HTTP Basic**.

---

# Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Apache Maven** para gerenciamento de dependências e construção do projeto
- **Spring Data JPA** para persistência de dados
- **PostgreSQL** como banco de dados relacional
- **Spring Security** para autenticação e autorização
- **BCrypt** para codificação segura de senhas

---

# Pré-requisitos

Para executar este projeto localmente, você precisa ter o seguinte software instalado:

## Java Development Kit (JDK) 17 ou superior
Você pode baixar o OpenJDK, Oracle JDK, etc. (ex: OpenJDK).

## Apache Maven 3.6 ou superior
Instruções de instalação podem ser encontradas no site oficial do Maven.

## PostgreSQL
- Instale o PostgreSQL e crie um banco de dados chamado `travel_agency_db`.
- Certifique-se de que o serviço PostgreSQL está rodando.

# Como Instalar e Rodar

Siga os passos abaixo para configurar e executar a aplicação em sua máquina local:

Siga os passos abaixo para configurar e executar a aplicação em sua máquina local:

1.  **Clone o Repositório:**
    ```bash
    git clone https://github.com/Gu1GIT/API_Agencia_Viagem_Dados_Seguranca.git
    ```
  
2.  **Navegue até a Pasta do Projeto:**
    ```bash
    cd API_Agencia_Viagem
    ```
3. **Configure o Banco de Dados:** Abra o arquivo:
    ```bash
    src/main/resources/application.properties.
    ```
    
    **Atualize as credenciais do banco de dados com as suas informações do PostgreSQL:**
     ```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/travel_agency_db
    spring.datasource.username=postgres
    spring.datasource.password=sua_senha_do_postgres # <-- ATUALIZE AQUI
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```

4.  **Construa o Projeto com Maven:**
    Este comando compila o código e empacota a aplicação em um arquivo JAR executável.
    ```bash
    mvn clean install
    ```

5.  **Execute a Aplicação Spring Boot:**
    Após a construção bem-sucedida, você pode iniciar a aplicação.
    ```bash
    mvn spring-boot:run
    ```
    A aplicação será iniciada e estará disponível em `http://localhost:8080`.

    **No primeiro startup, o** `CommandLineRunner`  **irá pré-carregar os seguintes usuários** (se não existirem):

      * **Usuário ADMIN:** `username: admin, password: adminpass, role: ROLE_ADMIN `     
      * **Usuário USER:** `username: user, password: userpass, role: ROLE_USER`

## Configuração CORS para Testes Locais

A API está configurada para permitir requisições de origens cruzadas (CORS), o que é necessário para testar a API com um front-end (como o arquivo `index.html` fornecido) que é carregado diretamente do sistema de arquivos ou de um servidor de desenvolvimento diferente.

A configuração de CORS está definida na classe `WebConfig.java`, permitindo requisições de `*` (qualquer origem) para todos os endpoints sob `/api/**`.

## Como Testar a API
### 1. Via cURL (Linha de Comando)
Você pode usar o curl para testar os endpoints diretamente do seu terminal. Certifique-se de que a aplicação Spring Boot esteja rodando.

  * **Registrar Novo Usuário:**
   ```bash
  curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -u admin:adminpass -d "{\"username\": \"novo_usuario\", \"password\": \"senha123\", \"role\": \"ROLE_USER\"}"
 ```
  * **Fazer Login:**
   ```bash
  curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"username\": \"user\", \"password\": \"userpass\"}"

  ```  

### 2. Via HTML Tester (Recomendado)
  Um arquivo `index.html` é fornecido na raiz do projeto para facilitar o teste interativo da API no navegador.
  
  1 **Abra o arquivo** index.html diretamente no seu navegador (`file:///caminho/para/seu/projeto/index.html`).
  
  2 **Certifique-se de que a API está rodando** (`mvn spring-boot:run`).
  
  3 Utilize os botões na página para interagir com os endpoints da API.
  
 *  Use as seções de "Autenticação e Acesso" para registrar novos usuários, fazer login e preencher automaticamente as credenciais para acesso protegido.
  
 *  As operações de destino exigirão que você preencha os campos "Usuário para Login" e "Senha para Login" com as credenciais de um usuário autenticado (como `user:userpass` ou `admin:adminpass`)


## Outros testes da API

### 1. Via cURL (Linha de Comando)

Você pode usar o `curl` para testar os endpoints diretamente do seu terminal. Certifique-se de que a aplicação Spring Boot esteja rodando.

* **Cadastrar Destino:**
    ```bash
  curl -X POST http://localhost:8080/api/destinations -H "Content-Type: application/json" -u user:userpass -d "{\"name\": \"Praia da Joaquina\", \"location\": \"Florianópolis, Brasil\", \"description\": \"Famosa pelas dunas e surf.\"}"
    ```
 * **Listar Todos os Destinos (Requer Autenticação HTTP Basic - use -u):**
    ```bash
   curl -X GET http://localhost:8080/api/destinations -u user:userpass
   ```
* **Pesquisar Destinos (ex: por "Florian"):**
    ```bash
    curl -X GET "http://localhost:8080/api/destinations/search?query=Florian" -u user:userpass
    ```
* **Ver Detalhes do Destino (ex: ID 1):**
    ```bash
    curl -X GET http://localhost:8080/api/destinations/1 -u user:userpass
    ```
* **Avaliar Destino (ex: ID 1 com nota 9):**
    ```bash
    curl -X PATCH "http://localhost:8080/api/destinations/1/rate?rating=9" -u user:userpass

    ```
* **Excluir Destino (ex: ID 1):**
    ```bash
    curl -X DELETE http://localhost:8080/api/destinations/1 -u user:userpass
    ```

### 2. Via HTML Tester

Um arquivo `index.html` simples é fornecido na raiz do projeto para facilitar o teste interativo da API no navegador.

1.  **Abra o arquivo `index.html`** diretamente no seu navegador).
2.  **Certifique-se de que a API está rodando (`mvn spring-boot:run`)**.
3.  Utilize os botões na página para interagir com os endpoints da API. A resposta será exibida na seção "Resposta da API" no topo da direito página.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

---

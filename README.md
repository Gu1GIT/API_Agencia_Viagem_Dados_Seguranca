# 🌍 API de Agência de Viagens

Este projeto consiste em uma API RESTful desenvolvida com **Java** e **Spring Boot**, criada para gerenciar destinos de viagens. A API permite operações **CRUD** (Create, Read, Update, Delete) básicas em destinos, além de funcionalidades de **pesquisa** e **avaliação**. Também inclui integração com banco de dados **PostgreSQL** e um sistema completo de segurança com **Spring Security** para autenticação e autorização de usuários.

> Este projeto foi desenvolvido como parte de um desafio de desenvolvimento de aplicações, focando em persistência de dados e segurança.

---

## 📋 Índice

- [Funcionalidades](#funcionalidades)
  - Gerenciamento de Destinos
  - Autenticação e Autorização
- Tecnologias Utilizadas
- Pré-requisitos
- [Como Instalar e Rodar](#como-instalar-e-rodar)
- Como Testar a API
  - Via cURL (Linha de Comando)
  - [Via HTML Tester (Recomendado)](#via-html-tester-recomendado)
- Contribuição
- Licença

---

## ✅ Funcionalidades

### Gerenciamento de Destinos

- **Cadastrar Destino**  
  Permite inserir um novo destino de viagem.  
  `POST /api/destinations`

- **Listar Destinos**  
  Retorna a lista de todos os destinos disponíveis.  
  `GET /api/destinations`

- **Pesquisar Destinos**  
  Permite pesquisar destinos por nome ou localização.  
  `GET /api/destinations/search?query={termo}`

- **Visualizar Detalhes do Destino**  
  Retorna informações detalhadas sobre um destino específico por ID.  
  `GET /api/destinations/{id}`

- **Avaliar Destino**  
  Permite atribuir uma nota (1-10) a um destino, atualizando sua média.  
  `PATCH /api/destinations/{id}/rate?rating={nota}`

- **Atualizar Destino**  
  Atualiza todas as informações de um destino existente por ID.  
  `PUT /api/destinations/{id}`

- **Excluir Destino**  
  Remove um destino específico por ID.  
  `DELETE /api/destinations/{id}`

# Autenticação e Autorização

## Registrar Usuário
Permite que novos usuários se cadastrem com um nome de usuário, senha e papel (ex: `ROLE_USER`, `ROLE_ADMIN`).

**Endpoint:**  
`POST /api/auth/register`  
**Acesso:** Público

## Fazer Login
Autentica um usuário existente.

**Endpoint:**  
`POST /api/auth/login`  
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

## 1. Clone o Repositório

```bash
git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git

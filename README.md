
# 🍽️ Eatz - API de Gestão para Restaurantes

**Eatz Register API** é uma API RESTful desenvolvida em **Java com Spring Boot**, voltada para facilitar a gestão de restaurantes e melhorar a experiência dos clientes ao consultar, avaliar e realizar pedidos online. A aplicação permite o gerenciamento de usuários (clientes e funcionários), endereços e autenticação com segurança via JWT.

---

## 📌 Problema

Restaurantes de pequeno e médio porte enfrentam dificuldades na gestão de pedidos, no relacionamento com clientes e na presença digital. Muitos não possuem sistemas próprios ou usam soluções incompletas, o que afeta tanto a operação quanto a experiência dos consumidores. Isso prejudica a competitividade e dificulta o crescimento dos negócios.

---

## 🎯 Objetivo

Desenvolver uma API back-end robusta com **Spring Boot**, capaz de:

- Gerenciar diferentes tipos de usuários (clientes e funcionários);
- Controlar dados de endereço, perfis e senhas;
- Proteger os dados com autenticação JWT;
- Fornecer endpoints RESTful bem estruturados;
- Servir como base para um ecossistema completo de serviços digitais para restaurantes.

---

## 🧱 Arquitetura

A API segue uma arquitetura em camadas inspirada em **DDD (Domain-Driven Design)**:

- **Domain**: Entidades de negócio, enums e interfaces de repositórios;
- **Application**: Casos de uso (Use Cases) como `CreateCustomerUseCase`;
- **Infrastructure**: Implementações de repositórios JPA, configuração de segurança (JWT), Swagger, etc.;
- **Presentation (Web)**: Controllers, DTOs de requisição/resposta e mapeadores.

---

## 🚀 Tecnologias Utilizadas

- Java 21 + Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Hibernate (JPA)
- Docker e Docker Compose
- Swagger/OpenAPI
- Postman

---

## 🔐 Segurança

- Autenticação via JWT com diferenciação de tipo de usuário (`CUSTOMER`, `RESTAURANT_USER`);
- Tokens expirados ou revogados são bloqueados;
- Handlers customizados para tratamento de exceções (`JwtAccessDeniedHandler`, etc.);
- Endpoints protegidos com regras específicas por tipo de usuário.

---

## 🧰 Como executar localmente

### ✅ Pré-requisitos

- Java 21
- Docker + Docker Compose
- Git

### 🔧 Clonando o projeto

```bash
git clone https://github.com/EatzFiap/register-api.git
cd register-api
```

### ▶️ Executando com Docker Compose

```bash
docker-compose up --build
```

A API será executada em `http://localhost:8080`.

### 🔗 Acesso ao banco PostgreSQL

- **Host**: localhost  
- **Porta**: 5432  
- **Usuário**: local  
- **Senha**: local123  
- **Banco**: register_db  

---

## 📄 Documentação da API

Acesse a documentação interativa no navegador:

🔗 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

> A documentação apresenta todos os endpoints, exemplos de requisição e resposta.

---

## 🧪 Testes com Postman

Você pode importar a collection e o environment do Postman disponível na pasta `docs/` do repositório.

Ou usar o link direto:  
[👉 Collection do Postman](https://eatzfiap.postman.co/workspace/Team-Workspace~efc4d0be-07f3-4213-8f2e-6d1dbc32593b/collection/44152001-ac1e5350-c5a5-4407-a83e-ae2c41721d84)

---

## ✅ Boas Práticas Utilizadas

- **Separação de responsabilidades (SRP)** e princípios **SOLID**
- Uso de **DTOs** e **validadores (`@NotBlank`, etc.)**
- Estrutura RESTful clara e padronizada
- Modularização com camadas independentes
- Reutilização de código (ex: herança entre `CustomerAddress` e `Address`)
- Tratamento de erros consistente e padronizado
- Documentação automática com Swagger

---

## 👥 Equipe

- Arthur Garcia – RM 364139  
- Kauã Leal – RM 363862  
- Luíza Rosa – RM 363912  

---

> Para dúvidas ou contribuições, abra uma issue ou envie um pull request. 💬

# 🤡 Clownstore! E-commerce utilizando microsserviços

[![en](https://img.shields.io/badge/language-en-red?style=for-the-badge&color=%23C5283D)](https://github.com/yura2312/Ecommerce-microservice/blob/main/README.md)

Backend de e-commerce utilizando a arquitetura de microsserviços construído com **Spring Boot** e **Spring Cloud**, utilizando bancos de dados SQL (PostgreSQL), NoSQL (MongoDB) e em memória (Redis), e protegido pelo **Keycloak** com os protocolos OAuth2/OIDC.

## Arquitetura

O sistema consiste em um Gateway centralizado o roteamento das requisições para os microsserviços específicos. A autenticação é gerenciada pelo Keycloak, e os tokens são passados para os serviços para validar as requisições.

| Serviço | Porta | Tecnologia | Banco de Dados | Descrição |
| --- | --- | --- | --- | --- |
| **Gateway** | `8080` | Spring Cloud Gateway (MVC) | Nenhum | Ponto de entrada, roteamento e Cliente OAuth2. |
| **Product** | `8081` | Spring Boot | MongoDB | Gerencia catálogo de produtos e estoque. |
| **Cart** | `8082` | Spring Boot | Redis | Gerencia carrinhos de compras dos usuários. |
| **Order** | `8083` | Spring Boot | PostgreSQL | Lida com criação de pedidos e persistência. |
| **Keycloak** | `9000` | Keycloak | H2 Embarcado | Provedor de Identidade (IdP) para Single Sign-On (SSO). |

## 🛠 Stack Tecnológica

* **Java:** 25
* **Framework:** Spring Boot 4.0.0
* **Bancos de Dados:**
* PostgreSQL (Pedidos/Orders)
* MongoDB (Produtos/Products)
* Redis (Carrinhos/Carts)


* **Segurança:** Keycloak, Spring Security, OAuth2 Client e Resource Server
* **Comunicação:** Spring Cloud OpenFeign
* **Ferramentas:** Docker Compose, Flyway, Lombok.

## 🚀 Começando

### Pré-requisitos

* **Java 25**
* **Docker** e **Docker Compose**
* **Maven**

### 1. Configuração da Infraestrutura

Inicie os bancos de dados necessários e o container do Keycloak usando o Docker Compose a partir do diretório raiz:

```bash
docker-compose up -d

```

Isso iniciará:

* **Keycloak** na porta `9000`
* **Redis** na porta `6379`
* **MongoDB** na porta `27017`
* **PostgreSQL** na porta `5432`

### 2. Configuração do Keycloak

Como o projeto depende do Keycloak para autenticação, você deve configurar o *realm* manualmente ou importar uma configuração:

1. Acesse o Keycloak em `http://localhost:9000`.
2. Faça login com `admin` / `admin`.
3. Crie um *realm* chamado **`clownstore`**.
4. Crie um cliente (*client*) chamado **`spring-security-keycloak`**.
* **Client Authentication:** On (Ativado)
* **Valid Redirect URIs:** `http://localhost:8080/login/oauth2/code/keycloak`
* **Authorization Grant Type:** Authorization Code


5. Crie um usuário para testes.

### 3. Variáveis de Ambiente

O serviço **Gateway** requer o Segredo do Cliente (*Client Secret*) do Keycloak. Você pode passar isso como uma variável de ambiente ou editar a configuração.

* `SECRET`: O *client secret* do seu cliente `spring-security-keycloak` no Keycloak.

### 4. Executando os Microsserviços

Você pode executar cada serviço em um terminal separado usando o Maven Wrapper:

**Product Service (Serviço de Produto)**

```bash
cd product
./mvnw spring-boot:run

```

**Cart Service (Serviço de Carrinho)**

```bash
cd cart
./mvnw spring-boot:run

```

**Order Service (Serviço de Pedido)**

```bash
cd order
./mvnw spring-boot:run

```

**Gateway Service (Serviço de Gateway)**

```bash
cd gateway
# Certifique-se de que a variável de ambiente SECRET está definida, ou substitua ${SECRET} no application.yaml
export SECRET=seu_keycloak_secret
./mvnw spring-boot:run

```

## Endpoints da API

Todas as requisições devem ser feitas através do Gateway (porta padrão `8080`).

**Rotas de Produto** (`/product/**`)

* `GET /product/all` - Lista todos os produtos.
* `GET /product/{name}` - Busca produto por nome.
* `POST /product/` - Cria um novo produto.
* `DELETE /product/{name}` - Deleta um produto.

**Rotas de Carrinho** (`/cart/**`)

* `POST /cart/?productId={id}&quantity={qty}` - Adiciona item ao carrinho.
* `GET /cart/?userId={id}` - Busca carrinho pelo ID do Usuário.

**Rotas de Pedido** (`/order/**`)

* `POST /order/save` - Converte o carrinho do usuário atual em um pedido.
* `DELETE /order/` - Deleta o histórico de pedidos do usuário.

## Banco de Dados e Migrações

* **Order Service:** Usa **Flyway** para migrações de banco de dados. O esquema inclui as tabelas `orders` e `order_item`.
* **Product Service:** Usa documentos MongoDB.
* **Cart Service:** Usa Hashes do Redis com a estrutura de chave mapeando para um objeto `Cart`.

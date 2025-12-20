# 🤡 Clownstore! Ecommerce using Microservices architecture

[![pt-br](https://img.shields.io/badge/language-pt--br-brightgreen?style=for-the-badge)](https://github.com/yura2312/Ecommerce-microservice/blob/main/README.pt-br.md)

A microservices-based e-commerce backend built with **Spring Boot** and **Spring Cloud**, utilizing SQL (PostgreSQL) and NoSQL (MongoDB) and in-memory database (Redis) and secured by **Keycloak** to comply with OAuth2/OIDC protocols.

##  Architecture

The system consists of a centralized Gateway that routes requests to specific microservices. Authentication is handled via Keycloak, and tokens are relayed to downstream services to validate requests.

| Service | Port | Technology | Database | Description |
| --- | --- | --- | --- | --- |
| **Gateway** | `8080` | Spring Cloud Gateway (MVC) | H2 (Default DB) | Entry point, routing, and OAuth2 Client. |
| **Product** | `8081` | Spring Boot | MongoDB | Manages product catalog and inventory. |
| **Cart** | `8082` | Spring Boot | Redis | Manages user shopping carts. |
| **Order** | `8083` | Spring Boot | PostgreSQL | Handles order creation and persistence. |

## 🛠 Tech Stack

* **Java:** 25
* **Framework:** Spring Boot 4.0.0
* **Databases:**
    * PostgreSQL (Orders)
    * MongoDB (Products)
    * Redis (Carts)
* **Security:** Keycloak, Spring Security, OAuth2 Client and Resource Server
* **Communication:** Spring Cloud OpenFeign
* **Tools:** Docker Compose, Flyway, Lombok.

## 🚀 Getting Started

### Prerequisites

* **Java 25** 
* **Docker** and **Docker Compose**
* **Maven**

### 1. Infrastructure Setup

Start the required databases and Keycloak container using Docker Compose from the root directory:

```bash
docker-compose up -d

```

This will start:

* **Keycloak** on port `9000`
* **Redis** on port `6379`
* **MongoDB** on port `27017`
* **PostgreSQL** on port `5432`

### 2. Keycloak Configuration

Since the project depends on Keycloak for authentication, you must configure the realm manually or import a configuration:

1. Access Keycloak at `http://localhost:9000`.
2. Login with `admin` / `admin`.
3. Create a realm named **`clownstore`**.
4. Create a client named **`spring-security-keycloak`**.
* **Client Authentication:** On
* **Valid Redirect URIs:** `http://localhost:8080/login/oauth2/code/keycloak`
* **Authorization Grant Type:** Authorization Code


5. Create a user for testing.

### 3. Environment Variables

The **Gateway** service requires the Keycloak Client Secret. You can pass this as an environment variable or edit the configuration.

* `SECRET`: The client secret from your Keycloak `spring-security-keycloak` client.

### 4. Running the Microservices

You can run each service in a separate terminal using the Maven Wrapper:

**Product Service**

```bash
cd product
./mvnw spring-boot:run

```

**Cart Service**

```bash
cd cart
./mvnw spring-boot:run

```

**Order Service**

```bash
cd order
./mvnw spring-boot:run

```

**Gateway Service**

```bash
cd gateway
# Ensure SECRET env var is set, or replace ${SECRET} in application.yaml
export SECRET=your_keycloak_secret
./mvnw spring-boot:run

```

##  API Endpoints

All requests should be made through the Gateway (default port `8080`).

**Product Routes** (`/product/**`)

* `GET /product/all` - List all products.
* `GET /product/{name}` - Find product by name.
* `POST /product/` - Create a new product.
* `DELETE /product/{name}` - Delete a product.

**Cart Routes** (`/cart/**`)

* `POST /cart/?productId={id}&quantity={qty}` - Add item to cart.
* `GET /cart/?userId={id}` - Get cart by User ID.

**Order Routes** (`/order/**`)

* `POST /order/save` - Convert current user's cart into an order.
* `DELETE /order/` - Delete order history for user.

## Database & Migrations

* **Order Service:** Uses **Flyway** for database migrations. The schema includes `orders` and `order_item` tables.
* **Product Service:** Uses MongoDB documents.
* **Cart Service:** Uses Redis Hashes with the key structure mapping to a `Cart` object.

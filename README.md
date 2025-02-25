# 🛒 Shopping Cart Microservices System

This project is a **microservices-based** shopping cart system designed for scalability and modularity. It consists of multiple microservices such as **Cart Service, Product Service, Order Service, and API Gateway** to handle various e-commerce operations efficiently.

## 🏗️ Project Structure
```
shopping-cart-backend/ 
├── apigateway/ # API Gateway to route and secure requests
├── cart-service/ # Manages shopping cart operations 
├── order-service/ # Handles order creation and retrieval 
├── product-service/ # Manages product catalog and pricing 
├── eureka-server/ # Service Discovery using Netflix Eureka 
```

## 🚀 Tech Stack

- **Spring Boot 3.x** - Microservices framework
- **Spring Cloud Gateway** - API Gateway
- **Spring Security + JWT** - Authentication & Authorization
- **Spring Data JPA + MariaDB** - Database Management
- **Netflix Eureka** - Service Discovery
- **WebClient** - Inter-service communication
- **Docker & Docker Compose** - Containerization
- **React.js** - Frontend integration

---

## 📌 Features

✅ **Cart Management:** Add, remove, and view cart items.  
✅ **Order Processing:** Convert cart items into orders.  
✅ **Product Management:** Fetch products and pricing dynamically.  
✅ **Security:** JWT-based authentication for API access.  
✅ **API Gateway:** Single entry point for all requests.  
✅ **Load Balancing:** Uses Eureka for service discovery.

---

## 🔧 Setup & Installation

### 1️⃣ Clone the Repository
```bash
git clone git@github.com:your-username/shopping-cart-backend.git
cd shopping-cart-backend
```

### 2️⃣ Configure Environment Variables
Modify application.properties for each microservice:

### API Gateway
server.port=8080
spring.cloud.gateway.routes[0].id=cart-service
spring.cloud.gateway.routes[0].uri=http://localhost:8082
spring.cloud.gateway.routes[0].predicates=Path=/cart/**

### 3️⃣ Run Services with Docker Compose
docker-compose up --build


API Endpoints
🛒 Cart Service (/cart)
- ** GET /cart - Get user’s cart
- ** POST /cart - Add item to cart
- ** DELETE /cart/{itemId} - Remove item from cart

📦 Order Service (/orders)
- ** POST /orders - Create an order
- ** GET /orders - Fetch user orders

🛍️ Product Service (/products)
- ** GET /products - Get all products
- ** GET /products/{id} - Fetch product details

📜 License
This project is licensed under the MIT License.
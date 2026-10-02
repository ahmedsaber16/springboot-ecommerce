# E-Commerce Platform

A REST API for an e-commerce store, built with Spring Boot.

## Features

- User registration and login (JWT authentication)
- Role-based access (Admin / User)
- Product and category management
- Shopping cart (add, update, remove items)
- Order checkout
- Wallet-based payment system

## Tech Stack

Java, Spring Boot, Spring Security, Spring Data JPA, JWT, Lombok, MySQL

## How to Run

```bash
git clone https://github.com/ahmedsaber16/springboot-ecommerce.git
cd springboot-ecommerce
./mvnw spring-boot:run
```

App runs on `http://localhost:8080`.

## Main Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login, returns a token |
| GET | /api/products/all | Get all products |
| POST | /api/products/add/{categoryId} | Add a product (Admin only) |
| GET | /api/categories/all | Get all categories |
| POST | /api/cart/add | Add item to cart |
| GET | /api/cart/mycart | View your cart |
| POST | /api/orders/checkout | Place an order |
| POST | /api/payments/pay | Pay for an order |

Protected endpoints require a JWT token:

```
Authorization: Bearer <token>
```

# Product Inventory REST API

## 1. Project Overview
This service is a Spring Boot application designed for managing warehouse or retail inventory. It enables full lifecycle tracking of products, handles validation, provides inventory alerts, and implements clean layered architecture.

## 2. Entity Details
- **Entity**: `Product`
- **Fields**:
  - `id` (Long, Primary Key, Auto-Increment)
  - `name` (String, Non-null, 2–150 characters)
  - `sku` (String, Unique identifier, Format: Alphanumeric)
  - `price` (BigDecimal, Positive decimal with 2 decimal places)
  - `stockQuantity` (Integer, Minimum value 0)
  - `category` (String, Non-null)

## 3. Technologies Used
- Java 17
- Spring Boot 3.3.4
- Spring Web
- Spring Data JPA
- Hibernate Validator / Jakarta Validation
- MySQL 8+
- Gradle
- Springdoc OpenAPI (Swagger 3)
- Docker

## 4. API Endpoints

| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/products` | Create a new product |
| `GET` | `/api/v1/products` | Retrieve paginated products (filters: `category`, `page`, `size`, `sort`) |
| `GET` | `/api/v1/products/{id}` | Fetch a product by ID |
| `PUT` | `/api/v1/products/{id}` | Update an existing product |
| `DELETE` | `/api/v1/products/{id}` | Delete a product by ID |
| `GET` | `/api/v1/products/alerts/low-stock?threshold=5` | Custom JPQL query for low-stock products |

### Example Request Body (`POST /api/v1/products`)
```json
{
  "name": "Wireless Ergonomic Mouse",
  "sku": "TECH-MOU-001",
  "price": 49.99,
  "stockQuantity": 25,
  "category": "Electronics"
}
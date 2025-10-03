# ShopFlow Product Service

## Service Description

The ShopFlow Product Service is responsible for managing the product catalog of the e-commerce platform. It provides RESTful endpoints for performing CRUD (Create, Read, Update, Delete) operations on products. The service also uses Redis for caching to improve performance.

## Tech Stack

- **Java 17**
- **Spring Boot 2.7.5**
- **Spring Data JPA** for database interaction.
- **Spring Data Redis** for caching.
- **PostgreSQL** as the primary database.
- **Redis** for in-memory caching.
- **Maven** for dependency management.

## How to Run in Dev Mode

To run the service in development mode, you can use the following Maven command:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

This will start the service on port `8082` and connect to the development database and Redis instance as configured in `src/main/resources/application-dev.properties`.

## API Endpoints

### Products

- **GET /api/products**

  Retrieves a list of all products.

- **GET /api/products/{id}**

  Retrieves a specific product by its ID.

- **POST /api/products**

  Creates a new product.

  **Example Request:**

  ```json
  {
    "name": "Laptop",
    "description": "A powerful laptop",
    "price": 1200.00,
    "quantity": 10
  }
  ```

- **PUT /api/products/{id}**

  Updates an existing product.

- **DELETE /api/products/{id}**

  Deletes a product by its ID.

- **GET /api/products/search?name={name}**

  Searches for products by name.

## Communication with Other Services

The Product Service communicates with other services through events. It uses Spring's internal `ApplicationEventPublisher` to publish events when a product is created, updated, or deleted. Other services can listen to these events to perform actions, such as updating their own data or sending notifications.

### Published Events

- **ProductCreatedEvent:** Published when a new product is created.
- **ProductUpdatedEvent:** Published when a product is updated.
- **ProductDeletedEvent:** Published when a product is deleted.

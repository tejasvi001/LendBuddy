# Lend Buddy Backend

Lend Buddy’s backend is a Spring Boot-based RESTful API designed to securely track money lent by users to others. It follows the MVC architecture and incorporates key technologies such as Spring Security, JWT, MySQL, OpenAPI, and Actuator for monitoring and documentation.

---

## Required Technologies & Frameworks

- **Spring Boot** (2.7.x or higher)
- **Spring MVC**
- **Spring Security**
- **JWT** (for stateless authentication)
- **MySQL** (version 5.7 or higher)
- **Spring Boot Actuator** (for monitoring)
- **Spring Boot Devtools** (for hot reload during development)
- **OpenAPI/Swagger** (for API documentation)
- **Maven/Gradle** (for build management)

---

## Major Functionality

- Tracks lent money to others via RESTful endpoints
- Secures routes using JWT authentication and Spring Security
- Provides monitoring endpoints using Actuator
- Documents APIs clearly using OpenAPI and Swagger UI

---

## API Routes

### User Routes

| Method | Endpoint      | Description                    |
|--------|---------------|--------------------------------|
| POST   | /auth/signup  | Register a new user            |
| POST   | /auth/login   | User authentication           |
| GET    | /users/me    | Retrieve logged-in user's profile |
| PUT    | /users/me    | Update logged-in user's profile |

### Lending Routes

| Method | Endpoint       | Description                       |
|--------|----------------|---------------------------------|
| POST   | /loan          | Create new lending entry         |
| GET    | /loan          | Get all lending entries for user |
| GET    | /loan/{id}     | Get details of a specific lending|
| DELETE | /lendings/{id} | Delete or cancel a lending entry |

### Actuator / Health Routes

| Method | Endpoint          | Description           |
|--------|-------------------|-----------------------|
| GET    | /actuator/health  | Health check endpoint |
| GET    | /actuator/info    | Application info      |

### OpenAPI Documentation

- Accessible via `/swagger-ui.html` or `/v3/api-docs`

---

## Getting Started

### Prerequisites

- Java 11 or higher
- MySQL 5.7 or higher
- Maven or Gradle

### Installation

1. Clone the repository:

git clone https://github.com/tejasvi001/lendbuddy.git
cd lendbuddy


2. Configure database connection in `src/main/resources/application.properties`.
3. Build and run the application:

./mvnw spring-boot:run


4. Access API documentation at:

http://localhost:8080/swagger-ui.html



---

## Architecture and Security

- Follows Spring MVC pattern for clear separation of concerns.
- Uses Spring Security and JWT for secure, stateless authentication.
- Application health and metrics exposed via Spring Boot Actuator.
- Fully documented API using OpenAPI (Swagger).

---

## Contribution

- Fork the repository and work on feature branches.
- Follow coding standards and write unit tests.
- Open pull requests with clear descriptions.

---

## License

This project is licensed under the MIT License.

---

This README provides a clear overview of the backend’s purpose, technologies, endpoints, and how to get started, making it useful for developers and maintainers.

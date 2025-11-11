# Management Apps - Spring Cloud Microservices

A comprehensive microservices architecture built with Spring Cloud for coordinated management applications, following Domain-Driven Design (DDD), SOLID principles, Clean Code, and Test-Driven Development (TDD).

## 🏗️ Architecture

This project implements a microservices architecture with the following components:

### Infrastructure Services
- **Eureka Server** (Port 8761): Service discovery and registration
- **API Gateway** (Port 8080): Single entry point for all client requests
- **Config Server** (Port 8888): Centralized configuration management

### Business Services
- **CRM Service** (Port 8081): Customer Relationship Management
- **POS Service** (Port 8082): Point of Sale system
- **Timesheet Service** (Port 8083): Time tracking and employee hours
- **Project Management Service** (Port 8084): Project and task management
- **Subscriptions Service** (Port 8085): Subscription management
- **Sales Service** (Port 8086): Sales operations and tracking
- **Employees Service** (Port 8087): Employee information management
- **Helpdesk Service** (Port 8088): Customer support tickets
- **Planning Service** (Port 8089): Resource and capacity planning
- **Inventory Service** (Port 8090): Inventory and stock management

### Data Layer
- **MongoDB**: NoSQL database for all services

## 🎯 Design Principles

### Domain-Driven Design (DDD)
Each service follows a layered architecture:
```
├── domain/              # Business logic layer
│   ├── model/          # Domain entities and aggregates
│   ├── repository/     # Repository interfaces
│   └── service/        # Domain services
├── application/        # Application layer
│   ├── dto/           # Data Transfer Objects
│   ├── mapper/        # DTO-Entity mappers
│   └── usecase/       # Application use cases
├── infrastructure/    # Infrastructure layer
│   └── config/        # Configuration classes
└── presentation/      # Presentation layer
    └── controller/    # REST controllers
```

### SOLID Principles
- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Liskov Substitution**: Subtypes must be substitutable for their base types
- **Interface Segregation**: Many specific interfaces over one general interface
- **Dependency Inversion**: Depend on abstractions, not concretions

### Additional Best Practices
- **DRY (Don't Repeat Yourself)**: Common code in `common-lib` module
- **YAGNI (You Aren't Gonna Need It)**: Implement only what's needed
- **Clean Code**: Self-documenting, readable, maintainable code
- **TDD (Test-Driven Development)**: Comprehensive unit and integration tests

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- Docker and Docker Compose (optional, for containerized deployment)
- MongoDB 7.0 or higher (if running locally without Docker)

### Running Locally

#### 1. Start MongoDB
```bash
docker run -d -p 27017:27017 --name mongodb \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=admin123 \
  mongo:7.0
```

#### 2. Build the Project
```bash
mvn clean install
```

#### 3. Start Services in Order

**Start Infrastructure Services:**
```bash
# Start Eureka Server
cd eureka-server
mvn spring-boot:run

# Start Config Server (in new terminal)
cd config-server
mvn spring-boot:run

# Start API Gateway (in new terminal)
cd api-gateway
mvn spring-boot:run
```

**Start Business Services:**
```bash
# Start CRM Service (in new terminal)
cd crm-service
mvn spring-boot:run

# Start other services similarly...
```

### Running with Docker Compose

```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## 📡 API Endpoints

### Access Through API Gateway
All services are accessible through the API Gateway at `http://localhost:8080`

#### CRM Service Examples:
```bash
# Get all customers
curl http://localhost:8080/api/crm/customers

# Create a customer
curl -X POST http://localhost:8080/api/crm/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "555-0001",
    "company": "Acme Corp",
    "address": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zipCode": "10001",
      "country": "USA"
    }
  }'

# Get customer by ID
curl http://localhost:8080/api/crm/customers/{id}

# Update customer
curl -X PUT http://localhost:8080/api/crm/customers/{id} \
  -H "Content-Type: application/json" \
  -d '{...}'

# Delete customer
curl -X DELETE http://localhost:8080/api/crm/customers/{id}
```

### Direct Service Access
Services can also be accessed directly during development:
- CRM: `http://localhost:8081/customers`
- POS: `http://localhost:8082/sales`
- Timesheet: `http://localhost:8083/entries`
- And so on...

## 🔍 Service Discovery

Access Eureka Dashboard: `http://localhost:8761`

All registered services will be visible here with their health status.

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Tests for Specific Service
```bash
cd crm-service
mvn test
```

### Test Coverage
The project aims for 80%+ test coverage, with 100% coverage for critical business logic.

## 📦 Project Structure

```
management-apps/
├── common-lib/                    # Shared utilities and DTOs
├── eureka-server/                 # Service discovery
├── api-gateway/                   # API Gateway
├── config-server/                 # Configuration server
├── crm-service/                   # CRM microservice
├── pos-service/                   # POS microservice
├── timesheet-service/             # Timesheet microservice
├── project-management-service/    # Project management microservice
├── subscriptions-service/         # Subscriptions microservice
├── sales-service/                 # Sales microservice
├── employees-service/             # Employees microservice
├── helpdesk-service/              # Helpdesk microservice
├── planning-service/              # Planning microservice
├── inventory-service/             # Inventory microservice
├── docker-compose.yml             # Docker composition
├── pom.xml                        # Parent POM
└── README.md                      # This file
```

## 🛠️ Technology Stack

- **Spring Boot 3.2.0**: Application framework
- **Spring Cloud 2023.0.0**: Microservices patterns
- **Spring Cloud Netflix Eureka**: Service discovery
- **Spring Cloud Gateway**: API Gateway
- **Spring Cloud Config**: Configuration management
- **Spring Data MongoDB**: Data persistence
- **MongoDB 7.0**: NoSQL database
- **Lombok**: Reduce boilerplate code
- **JUnit 5**: Unit testing
- **Testcontainers**: Integration testing
- **Maven**: Build tool
- **Docker**: Containerization

## 📚 Documentation

- See `.github/copilot-instructions.md` for comprehensive development guidelines
- Each service follows the same architectural patterns for consistency
- API documentation available through Swagger (when configured)

## 🔐 Security Considerations

- Never commit secrets to version control
- Use environment variables for sensitive data
- MongoDB uses authentication in production
- Implement proper authentication/authorization (OAuth2, JWT)
- Use HTTPS in production environments

## 🤝 Contributing

1. Follow the established architecture patterns
2. Write tests for new features
3. Follow SOLID principles and clean code practices
4. Update documentation as needed
5. Ensure all tests pass before committing

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- Initial implementation following DDD, SOLID, and Clean Code principles

## 🙏 Acknowledgments

- Spring Cloud team for excellent microservices patterns
- Domain-Driven Design by Eric Evans
- Clean Code by Robert C. Martin
# GitHub Copilot Instructions for Management Apps

## Project Overview
This is a microservices architecture built with Spring Cloud for coordinated management applications. The project follows Domain-Driven Design (DDD), SOLID principles, Clean Code, and Test-Driven Development (TDD).

## Architecture Principles

### 1. Domain-Driven Design (DDD)
- **Ubiquitous Language**: Use business domain terms consistently across code and documentation
- **Bounded Contexts**: Each microservice represents a bounded context
- **Aggregates**: Domain entities grouped by consistency boundaries
- **Value Objects**: Immutable objects defined by their attributes
- **Domain Events**: Capture important business events for inter-service communication
- **Repositories**: Abstract data access from domain logic

### 2. Layered Architecture
Each microservice follows this structure:
```
├── domain/              # Business logic (entities, value objects, domain services)
│   ├── model/          # Domain entities and aggregates
│   ├── repository/     # Repository interfaces (no implementation)
│   └── service/        # Domain services
├── application/        # Application use cases and orchestration
│   ├── dto/           # Data Transfer Objects
│   ├── mapper/        # DTO-Entity mappers
│   └── usecase/       # Application use cases
├── infrastructure/    # External concerns (DB, messaging, etc.)
│   ├── persistence/   # Repository implementations
│   └── config/        # Configuration classes
└── presentation/      # REST controllers
    └── controller/    # HTTP endpoints
```

### 3. SOLID Principles

#### Single Responsibility Principle (SRP)
- Each class should have only one reason to change
- Controllers handle HTTP concerns only
- Services handle business logic only
- Repositories handle data persistence only

#### Open/Closed Principle (OCP)
- Open for extension, closed for modification
- Use interfaces and abstract classes
- Leverage dependency injection

#### Liskov Substitution Principle (LSP)
- Derived classes must be substitutable for their base classes
- Use proper inheritance hierarchies

#### Interface Segregation Principle (ISP)
- Many client-specific interfaces are better than one general-purpose interface
- Don't force clients to depend on methods they don't use

#### Dependency Inversion Principle (DIP)
- Depend on abstractions, not concretions
- Use constructor injection
- Domain layer should not depend on infrastructure layer

### 4. Clean Code Practices

#### Naming Conventions
- **Classes**: PascalCase, noun (e.g., `CustomerService`, `OrderRepository`)
- **Interfaces**: PascalCase, prefix with 'I' or use descriptive name (e.g., `ICustomerRepository`, `CustomerRepository`)
- **Methods**: camelCase, verb (e.g., `findById`, `createCustomer`)
- **Variables**: camelCase, meaningful names (avoid `x`, `temp`, `data`)
- **Constants**: UPPER_SNAKE_CASE
- **Packages**: lowercase, singular nouns

#### Code Organization
- Keep methods small (< 20 lines)
- Avoid deep nesting (max 3 levels)
- One level of abstraction per method
- Use early returns to reduce nesting
- Separate commands from queries (CQS)

#### Comments
- Code should be self-documenting
- Use comments only when necessary to explain "why", not "what"
- Use JavaDoc for public APIs

### 5. DRY (Don't Repeat Yourself)
- Extract common logic to shared methods or classes
- Use inheritance and composition wisely
- Create utility classes for common operations
- Use Spring Boot starters for common configurations

### 6. YAGNI (You Aren't Gonna Need It)
- Implement only what is needed now
- Avoid speculative generality
- Don't add features "just in case"
- Keep it simple

### 7. Test-Driven Development (TDD)

#### Test Structure
```java
// Given - Setup test data and preconditions
// When - Execute the action being tested
// Then - Verify the expected outcome
```

#### Testing Layers
- **Unit Tests**: Test individual classes in isolation (use mocks)
- **Integration Tests**: Test interaction between components (use test containers)
- **E2E Tests**: Test complete user flows

#### Coverage Goals
- Aim for 80%+ code coverage
- 100% coverage for critical business logic
- Test edge cases and error scenarios

### 8. MongoDB Best Practices
- Use proper indexes for query performance
- Design schemas around query patterns
- Use embedded documents for 1:1 and 1:few relationships
- Use references for 1:many and many:many relationships
- Avoid large arrays (> 1000 elements)
- Use projections to limit returned fields

### 9. Spring Cloud Patterns

#### Service Discovery
- All services register with Eureka
- Use logical service names for inter-service communication

#### API Gateway
- Single entry point for all client requests
- Handle cross-cutting concerns (auth, rate limiting, logging)

#### Configuration Management
- Externalize configuration in Config Server
- Use profiles for different environments

#### Resilience
- Use Circuit Breaker pattern (Resilience4j)
- Implement retries with exponential backoff
- Set proper timeouts

### 10. Error Handling
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle specific exceptions
    // Return proper HTTP status codes
    // Include meaningful error messages
    // Log errors appropriately
}
```

### 11. Logging Best Practices
- Use SLF4J with Logback
- Log at appropriate levels (ERROR, WARN, INFO, DEBUG, TRACE)
- Include correlation IDs for distributed tracing
- Avoid logging sensitive information
- Use structured logging

### 12. Security Considerations
- Never commit secrets to version control
- Use environment variables for sensitive data
- Validate and sanitize all inputs
- Implement proper authentication and authorization
- Use HTTPS in production

## Development Workflow

### Before Writing Code
1. Understand the business requirement
2. Identify the bounded context
3. Design the domain model
4. Write test cases first (TDD)
5. Implement the solution
6. Refactor for clean code

### Code Review Checklist
- [ ] Follows SOLID principles
- [ ] Proper separation of concerns
- [ ] Tests are written and passing
- [ ] No code duplication (DRY)
- [ ] Follows naming conventions
- [ ] Proper error handling
- [ ] Documentation is updated
- [ ] No security vulnerabilities

## Useful Commands

### Build
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Run Specific Service
```bash
cd <service-name>
mvn spring-boot:run
```

### Docker Compose
```bash
docker-compose up -d
```

## Common Patterns

### Repository Pattern
```java
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
}
```

### Service Pattern
```java
@Service
@Transactional
public class CustomerService {
    private final CustomerRepository repository;
    
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
}
```

### Controller Pattern
```java
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    
    public CustomerController(CustomerService service) {
        this.service = service;
    }
}
```

## Additional Resources
- Spring Cloud Documentation: https://spring.io/projects/spring-cloud
- MongoDB Best Practices: https://www.mongodb.com/docs/manual/
- Domain-Driven Design: Eric Evans book
- Clean Code: Robert C. Martin book

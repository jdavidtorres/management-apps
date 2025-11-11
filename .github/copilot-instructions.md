# Copilot Instructions - Management Apps

## Project Overview
Integrated business management platform with multiple coordinated microservices using Spring Cloud:
- **CRM**: Customer relationship management
- **POS**: Point of sale system
- **Timesheet**: Employee hours tracking
- **Project Management**: Project planning and execution
- **Subscriptions**: Recurring billing and subscriptions
- **Sales**: Sales orders and quotes
- **Employees**: Employee management and HR
- **Helpdesk**: Customer support ticketing
- **Planning**: Resource and capacity planning
- **Inventory**: Stock and warehouse management

## Stack
- **Java 21** | **Gradle 9** | **Spring Boot 3.x** | **MongoDB** | **Angular 20**
- **Architecture**: Microservices (Spring Cloud) + DDD + Clean Architecture
- **Communication**: REST APIs + Spring Cloud Gateway + Service Discovery (Eureka)
- **Language**: English only (code, comments, commits)

---

## Core Principles

### 1. DDD (Domain-Driven Design)
**Layered Structure:**
```
domain/model/           # Entities with business logic
domain/valueobject/     # Immutable value objects
domain/repository/      # Repository interfaces (NOT implementations)
application/service/    # Use cases, orchestration
infrastructure/         # Repository implementations, external services
presentation/          # REST controllers
```

**✅ DO:**
```java
// Rich domain model
public class Order {
    private OrderId id;
    private Money amount;
    private OrderStatus status;
    
    public void approve() {
        if (status != PENDING) throw new IllegalStateException("Cannot approve");
        this.status = APPROVED;
    }
}

// Value object (immutable)
public record Money(BigDecimal amount) {
    public Money { if (amount.compareTo(ZERO) < 0) throw new IllegalArgumentException(); }
    public Money add(Money other) { return new Money(this.amount.add(other.amount)); }
}
```

**❌ DON'T:**
```java
// Anemic model (no behavior)
public class Order {
    private String status;
    public void setStatus(String status) { this.status = status; } // ❌
}
```

---

### 2. TDD (Test-Driven Development)
**Process**: RED → GREEN → REFACTOR

```java
// ✅ Test FIRST
@Test
@DisplayName("Should create order when partner has valid credit")
void shouldCreateOrderWhenPartnerHasValidCredit() {
    // Given
    Partner partner = new Partner("123", Money.of(10000));
    when(partnerRepo.findById("123")).thenReturn(Optional.of(partner));
    
    // When
    OrderId id = orderService.createOrder("123", Money.of(500));
    
    // Then
    assertThat(id).isNotNull();
    verify(orderRepo).save(any(Order.class));
}
```

---

### 3. SOLID Principles

**Single Responsibility (SRP)**
```java
// ✅ One responsibility
@RestController
class OrderController {
    private final OrderService service;
    
    @PostMapping("/orders")
    ResponseEntity<OrderDTO> create(@Valid @RequestBody CreateOrderRequest req) {
        return ResponseEntity.ok(OrderDTO.from(service.createOrder(req.toCommand())));
    }
}

// ❌ Multiple responsibilities
class OrderController {
    void create() {
        validate();    // ❌
        saveToDb();    // ❌
        sendEmail();   // ❌
    }
}
```

**Dependency Inversion (DIP)**
```java
// ✅ Depend on abstractions
public class OrderService {
    private final OrderRepository repo; // Interface
    
    public OrderService(OrderRepository repo) { this.repo = repo; }
}

// ❌ Depend on concretions
public class OrderService {
    private final MongoOrderRepository repo; // ❌ Concrete class
}
```

---

### 4. DRY (Don't Repeat Yourself)

```java
// ❌ Duplication
void createOrder() {
    if (amount <= 0) throw new ValidationException();
    if (partnerId == null) throw new ValidationException();
}
void updateOrder() {
    if (amount <= 0) throw new ValidationException(); // ❌ Duplicated
    if (partnerId == null) throw new ValidationException(); // ❌ Duplicated
}

// ✅ Extract common logic
private void validateOrder(Order order) {
    if (order.amount() <= 0) throw new ValidationException("Invalid amount");
    if (order.partnerId() == null) throw new ValidationException("Partner required");
}
```

**Use abstract classes:**
```java
public abstract class BaseService<T, ID> {
    protected abstract Repository<T, ID> getRepository();
    public Optional<T> findById(ID id) { return getRepository().findById(id); }
    public T save(T entity) { return getRepository().save(entity); }
}
```

---

## Project Structure

```
src/main/java/com/management/
├── partner/
│   ├── domain/
│   │   ├── model/Partner.java
│   │   ├── valueobject/PartnerId.java
│   │   └── repository/PartnerRepository.java (interface)
│   ├── application/
│   │   ├── service/PartnerService.java
│   │   └── dto/PartnerDTO.java
│   ├── infrastructure/
│   │   └── persistence/MongoPartnerRepository.java
│   └── presentation/
│       └── controller/PartnerController.java
webapp/
├── src/app/
│   ├── partner/
│   │   ├── services/partner.service.ts
│   │   ├── models/partner.model.ts
│   │   └── components/partner-list.component.ts
```

---

## Code Quality Rules

### Java

**✅ MUST:**
- Use Java 21 features (records, pattern matching, sealed classes)
- Constructor injection: `@RequiredArgsConstructor`
- Return `Optional<T>` instead of null
- Use meaningful names: `findPartnerById()` not `get()`
- Constants: `private static final int MAX_RETRIES = 3;`
- JavaDoc for public APIs

**❌ NEVER:**
- Field injection: `@Autowired private Service service;` ❌
- Return null: `return null;` ❌ Use `Optional.empty()`
- Magic numbers: `if (count > 5)` ❌ Use constants
- Generic exceptions: `catch (Exception e)` ❌ Be specific
- `System.out.println()` ❌ Use logger
- Commented code ❌ Delete it

### Angular

**✅ MUST:**
- Standalone components (Angular 20 default)
- Signals for reactive state
- OnPush change detection
- `async` pipe for subscriptions
- TypeScript strict mode
- Interfaces for all models

```typescript
// ✅ Good component
@Component({
  selector: 'app-partner-list',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PartnerListComponent {
  partners = signal<Partner[]>([]);
  
  constructor(private service: PartnerService) {}
  
  ngOnInit() {
    this.service.getAll().subscribe(data => this.partners.set(data));
  }
}
```

**❌ NEVER:**
- Subscribe without unsubscribe ❌ Use `async` pipe or `takeUntilDestroyed()`
- Use `any` type ❌ Use proper types
- Business logic in components ❌ Use services
- Direct DOM manipulation ❌ Use Angular APIs
- Hardcoded URLs ❌ Use environment files

---

## Quick Reference

**Naming:**
- Classes: `PascalCase` (OrderService)
- Methods: `camelCase` (findById)
- Constants: `UPPER_SNAKE_CASE` (MAX_RETRY_ATTEMPTS)

**Testing:**
- Test coverage > 80%
- Use Given-When-Then structure
- One assertion concept per test
- Mock external dependencies

**REST API:**
- Use proper HTTP verbs (GET, POST, PUT, DELETE)
- Return DTOs, not entities
- Validate with `@Valid`
- Return appropriate status codes

**Git Commits:**
```
feat(partner): add credit validation
fix(order): resolve duplicate order creation
docs(readme): update setup instructions
```

---

## Anti-Patterns to Avoid

❌ Anemic domain models (entities without behavior)  
❌ Service classes doing everything (God objects)  
❌ Tight coupling (concrete classes instead of interfaces)  
❌ Copy-paste code  
❌ Long methods (> 20 lines)  
❌ Deep nesting (> 3 levels)  
❌ Mixed languages (Spanish/English)  
❌ Ignoring errors silently  

---

**Remember**: Write tests first, keep it simple, make it work, make it clean.

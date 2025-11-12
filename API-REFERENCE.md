# API Reference Guide

Quick reference for all Management Apps API endpoints.

## Base URLs

### Via API Gateway (Recommended)
```
http://localhost:8080/api/{service}/{resource}
```

### Direct Access (Development)
```
http://localhost:{port}/{resource}
```

## Service Endpoints

### 1. CRM Service
**Base Path**: `/api/crm`  
**Direct Port**: 8081

#### Customers API

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/customers` | Get all customers | `curl http://localhost:8080/api/crm/customers` |
| GET | `/customers/{id}` | Get customer by ID | `curl http://localhost:8080/api/crm/customers/123` |
| POST | `/customers` | Create new customer | See below |
| PUT | `/customers/{id}` | Update customer | See below |
| DELETE | `/customers/{id}` | Delete customer | `curl -X DELETE http://localhost:8080/api/crm/customers/123` |

**Create Customer Example:**
```bash
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
```

### 2. POS Service
**Base Path**: `/api/pos`  
**Direct Port**: 8082

#### Sales API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/sales` | Get all sales |
| GET | `/sales/{id}` | Get sale by ID |
| POST | `/sales` | Create new sale |
| PUT | `/sales/{id}` | Update sale |
| DELETE | `/sales/{id}` | Delete sale |

**Create Sale Example:**
```bash
curl -X POST http://localhost:8080/api/pos/sales \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Product Sale",
    "description": "Sale of product X"
  }'
```

### 3. Timesheet Service
**Base Path**: `/api/timesheet`  
**Direct Port**: 8083

#### Time Entries API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/entries` | Get all time entries |
| GET | `/entries/{id}` | Get entry by ID |
| POST | `/entries` | Create new entry |
| PUT | `/entries/{id}` | Update entry |
| DELETE | `/entries/{id}` | Delete entry |

**Create Time Entry Example:**
```bash
curl -X POST http://localhost:8080/api/timesheet/entries \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Development Work",
    "description": "8 hours of coding"
  }'
```

### 4. Project Management Service
**Base Path**: `/api/projects`  
**Direct Port**: 8084

#### Projects API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/projects` | Get all projects |
| GET | `/projects/{id}` | Get project by ID |
| POST | `/projects` | Create new project |
| PUT | `/projects/{id}` | Update project |
| DELETE | `/projects/{id}` | Delete project |

### 5. Subscriptions Service
**Base Path**: `/api/subscriptions`  
**Direct Port**: 8085

#### Subscriptions API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/subscriptions` | Get all subscriptions |
| GET | `/subscriptions/{id}` | Get subscription by ID |
| POST | `/subscriptions` | Create new subscription |
| PUT | `/subscriptions/{id}` | Update subscription |
| DELETE | `/subscriptions/{id}` | Delete subscription |

### 6. Sales Service
**Base Path**: `/api/sales`  
**Direct Port**: 8086

#### Sales Orders API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/salesorders` | Get all sales orders |
| GET | `/salesorders/{id}` | Get order by ID |
| POST | `/salesorders` | Create new order |
| PUT | `/salesorders/{id}` | Update order |
| DELETE | `/salesorders/{id}` | Delete order |

### 7. Employees Service
**Base Path**: `/api/employees`  
**Direct Port**: 8087

#### Employees API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/employees` | Get all employees |
| GET | `/employees/{id}` | Get employee by ID |
| POST | `/employees` | Create new employee |
| PUT | `/employees/{id}` | Update employee |
| DELETE | `/employees/{id}` | Delete employee |

### 8. Helpdesk Service
**Base Path**: `/api/helpdesk`  
**Direct Port**: 8088

#### Tickets API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tickets` | Get all tickets |
| GET | `/tickets/{id}` | Get ticket by ID |
| POST | `/tickets` | Create new ticket |
| PUT | `/tickets/{id}` | Update ticket |
| DELETE | `/tickets/{id}` | Delete ticket |

### 9. Planning Service
**Base Path**: `/api/planning`  
**Direct Port**: 8089

#### Plans API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/plans` | Get all plans |
| GET | `/plans/{id}` | Get plan by ID |
| POST | `/plans` | Create new plan |
| PUT | `/plans/{id}` | Update plan |
| DELETE | `/plans/{id}` | Delete plan |

### 10. Inventory Service
**Base Path**: `/api/inventory`  
**Direct Port**: 8090

#### Products API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/products` | Get all products |
| GET | `/products/{id}` | Get product by ID |
| POST | `/products` | Create new product |
| PUT | `/products/{id}` | Update product |
| DELETE | `/products/{id}` | Delete product |

## Common Response Formats

### Success Response (200 OK)
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "createdAt": "2024-01-01T00:00:00",
  "updatedAt": "2024-01-01T00:00:00"
}
```

### Error Response (4xx/5xx)
```json
{
  "timestamp": "2024-01-01T00:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found with id: '123'"
}
```

### Validation Error (400 Bad Request)
```json
{
  "name": "Name is required",
  "email": "Email should be valid"
}
```

## HTTP Status Codes

| Code | Meaning | When Used |
|------|---------|-----------|
| 200 | OK | Successful GET, PUT |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Validation error |
| 404 | Not Found | Resource doesn't exist |
| 500 | Internal Server Error | Server error |

## Testing with cURL

### Get All Resources
```bash
curl http://localhost:8080/api/{service}/{resource}
```

### Get One Resource
```bash
curl http://localhost:8080/api/{service}/{resource}/{id}
```

### Create Resource
```bash
curl -X POST http://localhost:8080/api/{service}/{resource} \
  -H "Content-Type: application/json" \
  -d '{...}'
```

### Update Resource
```bash
curl -X PUT http://localhost:8080/api/{service}/{resource}/{id} \
  -H "Content-Type: application/json" \
  -d '{...}'
```

### Delete Resource
```bash
curl -X DELETE http://localhost:8080/api/{service}/{resource}/{id}
```

## Testing with httpie

### Get All
```bash
http GET http://localhost:8080/api/{service}/{resource}
```

### Create
```bash
http POST http://localhost:8080/api/{service}/{resource} \
  name="Example" description="Test"
```

## Postman Collection

Import the following base URL and variables:
```
Base URL: {{baseUrl}}/api/{{service}}/{{resource}}
baseUrl: http://localhost:8080
```

## Common Query Parameters (Future)

| Parameter | Description | Example |
|-----------|-------------|---------|
| page | Page number | ?page=1 |
| size | Items per page | ?size=20 |
| sort | Sort field | ?sort=name,asc |
| filter | Filter criteria | ?filter=active:true |

## Authentication (Future)

All endpoints will require JWT token:
```bash
curl http://localhost:8080/api/crm/customers \
  -H "Authorization: Bearer {token}"
```

## Rate Limiting (Future)

| Endpoint | Limit |
|----------|-------|
| GET | 100 req/min |
| POST/PUT/DELETE | 50 req/min |

## Service Health Checks

### Eureka Dashboard
```
http://localhost:8761
```

### Service Health (Future)
```
http://localhost:{port}/actuator/health
```

### Service Info (Future)
```
http://localhost:{port}/actuator/info
```

## WebSocket Support (Future)

Real-time updates via WebSocket:
```javascript
const ws = new WebSocket('ws://localhost:8080/ws/{service}');
```

## GraphQL Support (Future)

Query multiple services in one request:
```graphql
{
  customer(id: "123") {
    name
    email
  }
  orders(customerId: "123") {
    id
    total
  }
}
```

## Best Practices

1. **Always use API Gateway** in production
2. **Include Content-Type header** for POST/PUT
3. **Handle errors gracefully** in client code
4. **Use HTTPS** in production
5. **Implement retry logic** for transient failures
6. **Cache frequently accessed data**
7. **Paginate large result sets**
8. **Validate input** on client side
9. **Log correlation IDs** for tracing
10. **Monitor API usage** and performance

## Support

For API support:
- Check the [SETUP.md](SETUP.md) for troubleshooting
- Review the [ARCHITECTURE.md](ARCHITECTURE.md) for design details
- Consult the [README.md](README.md) for general information

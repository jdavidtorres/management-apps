# Setup Guide for Management Apps

This guide will help you set up the development environment and run the Management Apps microservices.

## Prerequisites

### Required Software
- **Java 17 or higher**: [Download OpenJDK](https://adoptium.net/)
- **Maven 3.8+**: [Download Maven](https://maven.apache.org/download.cgi)
- **MongoDB 7.0+**: [Download MongoDB](https://www.mongodb.com/try/download/community)
- **Git**: [Download Git](https://git-scm.com/downloads)

### Optional (for Docker deployment)
- **Docker**: [Download Docker](https://www.docker.com/products/docker-desktop)
- **Docker Compose**: Usually included with Docker Desktop

## Installation Steps

### 1. Clone the Repository
```bash
git clone https://github.com/jdavidtorres/management-apps.git
cd management-apps
```

### 2. Verify Java Installation
```bash
java -version
# Should show Java 17 or higher
```

### 3. Verify Maven Installation
```bash
mvn -version
# Should show Maven 3.8 or higher
```

## Running the Application

### Option 1: Using Docker Compose (Recommended)

This is the easiest way to run all services including MongoDB.

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

### Option 2: Running Locally

#### Step 1: Start MongoDB
```bash
# Using Docker
docker run -d -p 27017:27017 --name mongodb mongo:7.0

# Or use your locally installed MongoDB
mongod --dbpath /your/data/path
```

#### Step 2: Build the Project
```bash
mvn clean install
```

#### Step 3: Start Services

You can either use the provided script:
```bash
./start-services.sh
```

Or start services manually in separate terminals:

**Infrastructure Services (start in order):**
```bash
# Terminal 1: Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2: Config Server (wait for Eureka to start)
cd config-server
mvn spring-boot:run

# Terminal 3: API Gateway (wait for Config Server)
cd api-gateway
mvn spring-boot:run
```

**Business Services (can start in parallel):**
```bash
# Terminal 4: CRM Service
cd crm-service
mvn spring-boot:run

# Terminal 5: POS Service
cd pos-service
mvn spring-boot:run

# ... and so on for other services
```

## Verifying the Installation

### 1. Check Eureka Dashboard
Open browser to: http://localhost:8761

You should see all services registered.

### 2. Test API Gateway
```bash
curl http://localhost:8080/api/crm/customers
```

Should return an empty array or sample customers.

### 3. Create a Test Customer
```bash
curl -X POST http://localhost:8080/api/crm/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "phone": "555-0001",
    "company": "Test Corp",
    "address": {
      "street": "123 Main St",
      "city": "Test City",
      "state": "TC",
      "zipCode": "12345",
      "country": "Test Country"
    }
  }'
```

## Service Ports

| Service | Port | URL |
|---------|------|-----|
| Eureka Server | 8761 | http://localhost:8761 |
| Config Server | 8888 | http://localhost:8888 |
| API Gateway | 8080 | http://localhost:8080 |
| CRM Service | 8081 | http://localhost:8081 |
| POS Service | 8082 | http://localhost:8082 |
| Timesheet Service | 8083 | http://localhost:8083 |
| Project Management | 8084 | http://localhost:8084 |
| Subscriptions | 8085 | http://localhost:8085 |
| Sales Service | 8086 | http://localhost:8086 |
| Employees Service | 8087 | http://localhost:8087 |
| Helpdesk Service | 8088 | http://localhost:8088 |
| Planning Service | 8089 | http://localhost:8089 |
| Inventory Service | 8090 | http://localhost:8090 |

## Development Tips

### Running Tests
```bash
# Run all tests
mvn test

# Run tests for a specific service
cd crm-service
mvn test

# Run with coverage
mvn clean test jacoco:report
```

### Building without Tests
```bash
mvn clean install -DskipTests
```

### Cleaning Build Artifacts
```bash
mvn clean
```

### Hot Reload with Spring Boot DevTools
Add to any service's POM:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

## Troubleshooting

### Service Won't Start
1. Check if the port is already in use
2. Ensure MongoDB is running
3. Check logs for errors
4. Verify Java and Maven versions

### Can't Connect to MongoDB
```bash
# Check if MongoDB is running
docker ps | grep mongodb

# Or for local installation
ps aux | grep mongod

# Test connection
mongo --eval "db.version()"
```

### Eureka Shows Services as DOWN
- Wait a few seconds for registration
- Check network connectivity
- Verify service is actually running
- Check application.yml configuration

### Build Failures
```bash
# Clean and rebuild
mvn clean install -U

# Update dependencies
mvn dependency:resolve
```

## IDE Setup

### IntelliJ IDEA
1. Import project as Maven project
2. Enable annotation processing (for Lombok)
3. Install Lombok plugin
4. Set Java SDK to 17

### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Import project

### Eclipse
1. Import as Maven project
2. Install Lombok
3. Enable annotation processing

## Next Steps

1. Explore the [Copilot Instructions](.github/copilot-instructions.md) for development guidelines
2. Read the main [README.md](README.md) for architecture details
3. Check out the API documentation (when Swagger is configured)
4. Start developing your features!

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [MongoDB Documentation](https://docs.mongodb.com/)
- [Docker Documentation](https://docs.docker.com/)

## Support

For issues and questions:
1. Check the troubleshooting section above
2. Review the Copilot instructions
3. Open an issue on GitHub

package com.management.crm.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "CRM Service API",
        version = "1.0.0",
        description = "Customer Relationship Management API for managing customer information",
        contact = @Contact(
            name = "Management Apps Team",
            email = "support@management-apps.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8081",
            description = "Development server (direct access)"
        ),
        @Server(
            url = "http://localhost:8080/api/crm",
            description = "Development server (via API Gateway)"
        )
    }
)
public class OpenApiConfig {
}

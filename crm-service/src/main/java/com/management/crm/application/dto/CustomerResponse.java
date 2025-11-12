package com.management.crm.application.dto;

import com.management.crm.domain.model.CustomerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer information response")
public class CustomerResponse {
    @Schema(description = "Customer unique identifier", example = "507f1f77bcf86cd799439011")
    private String id;
    
    @Schema(description = "Customer full name", example = "John Doe")
    private String name;
    
    @Schema(description = "Customer email address", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "Customer phone number", example = "+1-555-0001")
    private String phone;
    
    @Schema(description = "Customer company name", example = "Acme Corp")
    private String company;
    
    @Schema(description = "Customer address")
    private AddressDto address;
    
    @Schema(description = "Customer status", example = "ACTIVE")
    private CustomerStatus status;
    
    @Schema(description = "Customer creation timestamp")
    private LocalDateTime createdAt;
    
    @Schema(description = "Customer last update timestamp")
    private LocalDateTime updatedAt;
}

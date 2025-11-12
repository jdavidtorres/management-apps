package com.management.crm.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer creation/update request")
public class CustomerRequest {
    
    @Schema(description = "Customer full name", example = "John Doe", required = true)
    @NotBlank(message = "Name is required")
    private String name;
    
    @Schema(description = "Customer email address", example = "john.doe@example.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Schema(description = "Customer phone number", example = "+1-555-0001")
    private String phone;
    
    @Schema(description = "Customer company name", example = "Acme Corp")
    private String company;
    
    @Schema(description = "Customer address")
    private AddressDto address;
}

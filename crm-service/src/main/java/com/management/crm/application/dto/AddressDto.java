package com.management.crm.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Address information")
public class AddressDto {
    @Schema(description = "Street address", example = "123 Main St")
    private String street;
    
    @Schema(description = "City", example = "New York")
    private String city;
    
    @Schema(description = "State or province", example = "NY")
    private String state;
    
    @Schema(description = "ZIP or postal code", example = "10001")
    private String zipCode;
    
    @Schema(description = "Country", example = "USA")
    private String country;
}

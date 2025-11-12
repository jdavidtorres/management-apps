package com.management.crm.application.mapper;

import com.management.crm.application.dto.AddressDto;
import com.management.crm.application.dto.CustomerRequest;
import com.management.crm.application.dto.CustomerResponse;
import com.management.crm.domain.model.Address;
import com.management.crm.domain.model.Customer;
import com.management.crm.domain.model.CustomerStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerMapper {
    
    public Customer toEntity(CustomerRequest request) {
        return Customer.builder()
            .name(request.getName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .company(request.getCompany())
            .address(toAddressEntity(request.getAddress()))
            .status(CustomerStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }
    
    public CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
            .id(customer.getId())
            .name(customer.getName())
            .email(customer.getEmail())
            .phone(customer.getPhone())
            .company(customer.getCompany())
            .address(toAddressDto(customer.getAddress()))
            .status(customer.getStatus())
            .createdAt(customer.getCreatedAt())
            .updatedAt(customer.getUpdatedAt())
            .build();
    }
    
    public void updateEntity(Customer customer, CustomerRequest request) {
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setCompany(request.getCompany());
        customer.setAddress(toAddressEntity(request.getAddress()));
        customer.setUpdatedAt(LocalDateTime.now());
    }
    
    private Address toAddressEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        return Address.builder()
            .street(dto.getStreet())
            .city(dto.getCity())
            .state(dto.getState())
            .zipCode(dto.getZipCode())
            .country(dto.getCountry())
            .build();
    }
    
    private AddressDto toAddressDto(Address address) {
        if (address == null) {
            return null;
        }
        return AddressDto.builder()
            .street(address.getStreet())
            .city(address.getCity())
            .state(address.getState())
            .zipCode(address.getZipCode())
            .country(address.getCountry())
            .build();
    }
}

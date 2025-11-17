package com.management.crm.domain.service;

import com.management.crm.domain.model.Customer;
import com.management.crm.domain.model.CustomerStatus;
import com.management.crm.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Domain Service Tests")
class CustomerDomainServiceTest {
    
    @Mock
    private CustomerRepository customerRepository;
    
    @InjectMocks
    private CustomerDomainService customerDomainService;
    
    private Customer customer;
    
    @BeforeEach
    void setUp() {
        customer = Customer.builder()
            .id("1")
            .email("test@example.com")
            .name("Test User")
            .status(CustomerStatus.ACTIVE)
            .build();
    }
    
    @Test
    @DisplayName("Should return true when email is unique")
    void shouldReturnTrueWhenEmailIsUnique() {
        // Given
        when(customerRepository.existsByEmail("unique@example.com")).thenReturn(false);
        
        // When
        boolean result = customerDomainService.isEmailUnique("unique@example.com");
        
        // Then
        assertThat(result).isTrue();
    }
    
    @Test
    @DisplayName("Should return false when email already exists")
    void shouldReturnFalseWhenEmailExists() {
        // Given
        when(customerRepository.existsByEmail("existing@example.com")).thenReturn(true);
        
        // When
        boolean result = customerDomainService.isEmailUnique("existing@example.com");
        
        // Then
        assertThat(result).isFalse();
    }
    
    @Test
    @DisplayName("Should validate customer creation successfully")
    void shouldValidateCustomerCreationSuccessfully() {
        // Given
        when(customerRepository.existsByEmail(customer.getEmail())).thenReturn(false);
        
        // When & Then
        customerDomainService.validateCustomerCreation(customer);
    }
    
    @Test
    @DisplayName("Should throw exception when creating customer with existing email")
    void shouldThrowExceptionWhenCreatingCustomerWithExistingEmail() {
        // Given
        when(customerRepository.existsByEmail(customer.getEmail())).thenReturn(true);
        
        // When & Then
        assertThatThrownBy(() -> customerDomainService.validateCustomerCreation(customer))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email already exists");
    }
    
    @Test
    @DisplayName("Should validate customer update successfully")
    void shouldValidateCustomerUpdateSuccessfully() {
        // Given
        when(customerRepository.findByEmail(customer.getEmail()))
            .thenReturn(Optional.of(customer));
        
        // When & Then
        customerDomainService.validateCustomerUpdate(customer);
    }
}

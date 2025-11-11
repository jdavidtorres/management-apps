package com.management.crm.application.usecase;

import com.management.crm.application.dto.CustomerRequest;
import com.management.crm.application.dto.CustomerResponse;
import com.management.crm.application.mapper.CustomerMapper;
import com.management.crm.domain.model.Customer;
import com.management.crm.domain.model.CustomerStatus;
import com.management.crm.domain.repository.CustomerRepository;
import com.management.crm.domain.service.CustomerDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Create Customer Use Case Tests")
class CreateCustomerUseCaseTest {
    
    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private CustomerDomainService customerDomainService;
    
    @Mock
    private CustomerMapper customerMapper;
    
    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;
    
    private CustomerRequest request;
    private Customer customer;
    private CustomerResponse response;
    
    @BeforeEach
    void setUp() {
        request = CustomerRequest.builder()
            .name("Test User")
            .email("test@example.com")
            .phone("1234567890")
            .company("Test Company")
            .build();
        
        customer = Customer.builder()
            .id("1")
            .name("Test User")
            .email("test@example.com")
            .phone("1234567890")
            .company("Test Company")
            .status(CustomerStatus.ACTIVE)
            .build();
        
        response = CustomerResponse.builder()
            .id("1")
            .name("Test User")
            .email("test@example.com")
            .phone("1234567890")
            .company("Test Company")
            .status(CustomerStatus.ACTIVE)
            .build();
    }
    
    @Test
    @DisplayName("Should create customer successfully")
    void shouldCreateCustomerSuccessfully() {
        // Given
        when(customerMapper.toEntity(request)).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(response);
        
        // When
        CustomerResponse result = createCustomerUseCase.execute(request);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(request.getEmail());
        assertThat(result.getName()).isEqualTo(request.getName());
        
        verify(customerDomainService, times(1)).validateCustomerCreation(customer);
        verify(customerRepository, times(1)).save(customer);
    }
}

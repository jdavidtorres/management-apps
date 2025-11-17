package com.management.crm.application.usecase;

import com.management.crm.application.dto.CustomerRequest;
import com.management.crm.application.dto.CustomerResponse;
import com.management.crm.application.mapper.CustomerMapper;
import com.management.crm.domain.model.Customer;
import com.management.crm.domain.repository.CustomerRepository;
import com.management.crm.domain.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCustomerUseCase {
    
    private final CustomerRepository customerRepository;
    private final CustomerDomainService customerDomainService;
    private final CustomerMapper customerMapper;
    
    @Transactional
    public CustomerResponse execute(CustomerRequest request) {
        log.info("Creating customer with email: {}", request.getEmail());
        
        Customer customer = customerMapper.toEntity(request);
        customerDomainService.validateCustomerCreation(customer);
        
        Customer savedCustomer = customerRepository.save(customer);
        
        log.info("Customer created successfully with id: {}", savedCustomer.getId());
        return customerMapper.toResponse(savedCustomer);
    }
}

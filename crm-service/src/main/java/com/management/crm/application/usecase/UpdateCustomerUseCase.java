package com.management.crm.application.usecase;

import com.management.common.exception.ResourceNotFoundException;
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
public class UpdateCustomerUseCase {
    
    private final CustomerRepository customerRepository;
    private final CustomerDomainService customerDomainService;
    private final CustomerMapper customerMapper;
    
    @Transactional
    public CustomerResponse execute(String id, CustomerRequest request) {
        log.info("Updating customer with id: {}", id);
        
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        
        customerMapper.updateEntity(customer, request);
        customerDomainService.validateCustomerUpdate(customer);
        
        Customer updatedCustomer = customerRepository.save(customer);
        
        log.info("Customer updated successfully with id: {}", updatedCustomer.getId());
        return customerMapper.toResponse(updatedCustomer);
    }
}

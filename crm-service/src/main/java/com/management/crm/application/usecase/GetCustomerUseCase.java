package com.management.crm.application.usecase;

import com.management.common.exception.ResourceNotFoundException;
import com.management.crm.application.dto.CustomerResponse;
import com.management.crm.application.mapper.CustomerMapper;
import com.management.crm.domain.model.Customer;
import com.management.crm.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetCustomerUseCase {
    
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    
    @Transactional(readOnly = true)
    public CustomerResponse getById(String id) {
        log.info("Fetching customer with id: {}", id);
        
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        
        return customerMapper.toResponse(customer);
    }
    
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        log.info("Fetching all customers");
        
        return customerRepository.findAll()
            .stream()
            .map(customerMapper::toResponse)
            .collect(Collectors.toList());
    }
}

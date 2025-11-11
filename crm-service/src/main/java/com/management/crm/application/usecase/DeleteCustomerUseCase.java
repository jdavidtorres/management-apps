package com.management.crm.application.usecase;

import com.management.common.exception.ResourceNotFoundException;
import com.management.crm.domain.model.Customer;
import com.management.crm.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteCustomerUseCase {
    
    private final CustomerRepository customerRepository;
    
    @Transactional
    public void execute(String id) {
        log.info("Deleting customer with id: {}", id);
        
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        
        customerRepository.delete(customer);
        
        log.info("Customer deleted successfully with id: {}", id);
    }
}

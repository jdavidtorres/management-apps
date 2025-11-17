package com.management.crm.domain.service;

import com.management.crm.domain.model.Customer;
import com.management.crm.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerDomainService {
    
    private final CustomerRepository customerRepository;
    
    public boolean isEmailUnique(String email) {
        return !customerRepository.existsByEmail(email);
    }
    
    public boolean isEmailUniqueExcludingId(String email, String customerId) {
        return customerRepository.findByEmail(email)
            .map(customer -> customer.getId().equals(customerId))
            .orElse(true);
    }
    
    public void validateCustomerCreation(Customer customer) {
        if (!isEmailUnique(customer.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
    }
    
    public void validateCustomerUpdate(Customer customer) {
        if (!isEmailUniqueExcludingId(customer.getEmail(), customer.getId())) {
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
    }
}

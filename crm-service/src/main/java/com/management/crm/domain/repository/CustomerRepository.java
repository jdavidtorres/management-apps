package com.management.crm.domain.repository;

import com.management.crm.domain.model.Customer;
import com.management.crm.domain.model.CustomerStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    
    Optional<Customer> findByEmail(String email);
    
    List<Customer> findByStatus(CustomerStatus status);
    
    List<Customer> findByCompany(String company);
    
    boolean existsByEmail(String email);
}

package com.management.crm.presentation.controller;

import com.management.crm.application.dto.CustomerRequest;
import com.management.crm.application.dto.CustomerResponse;
import com.management.crm.application.usecase.CreateCustomerUseCase;
import com.management.crm.application.usecase.DeleteCustomerUseCase;
import com.management.crm.application.usecase.GetCustomerUseCase;
import com.management.crm.application.usecase.UpdateCustomerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    
    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        log.info("REST request to create customer: {}", request.getEmail());
        CustomerResponse response = createCustomerUseCase.execute(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
        log.info("REST request to get customer: {}", id);
        CustomerResponse response = getCustomerUseCase.getById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        log.info("REST request to get all customers");
        List<CustomerResponse> response = getCustomerUseCase.getAll();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody CustomerRequest request) {
        log.info("REST request to update customer: {}", id);
        CustomerResponse response = updateCustomerUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        log.info("REST request to delete customer: {}", id);
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}

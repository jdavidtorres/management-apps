package com.management.employees.presentation.controller;

import com.management.employees.domain.model.Employee;
import com.management.employees.domain.repository.EmployeeRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/employeess")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    
    private final EmployeeRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        log.info("Fetching all employeess");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable String id) {
        log.info("Fetching employees with id: {}", id);
        Employee entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee entity) {
        log.info("Creating new employees");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Employee saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable String id, @RequestBody Employee entity) {
        log.info("Updating employees with id: {}", id);
        Employee existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        Employee updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting employees with id: {}", id);
        Employee entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

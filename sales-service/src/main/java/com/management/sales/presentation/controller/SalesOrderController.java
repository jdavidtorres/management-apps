package com.management.sales.presentation.controller;

import com.management.sales.domain.model.SalesOrder;
import com.management.sales.domain.repository.SalesOrderRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/saless")
@RequiredArgsConstructor
@Slf4j
public class SalesOrderController {
    
    private final SalesOrderRepository repository;
    
    @GetMapping
    public ResponseEntity<List<SalesOrder>> getAll() {
        log.info("Fetching all saless");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SalesOrder> getById(@PathVariable String id) {
        log.info("Fetching sales with id: {}", id);
        SalesOrder entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("SalesOrder", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<SalesOrder> create(@RequestBody SalesOrder entity) {
        log.info("Creating new sales");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        SalesOrder saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SalesOrder> update(@PathVariable String id, @RequestBody SalesOrder entity) {
        log.info("Updating sales with id: {}", id);
        SalesOrder existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("SalesOrder", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        SalesOrder updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting sales with id: {}", id);
        SalesOrder entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("SalesOrder", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

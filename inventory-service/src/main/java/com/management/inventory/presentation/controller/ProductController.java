package com.management.inventory.presentation.controller;

import com.management.inventory.domain.model.Product;
import com.management.inventory.domain.repository.ProductRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/inventorys")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    
    private final ProductRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        log.info("Fetching all inventorys");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        log.info("Fetching inventory with id: {}", id);
        Product entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product entity) {
        log.info("Creating new inventory");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Product saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product entity) {
        log.info("Updating inventory with id: {}", id);
        Product existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        Product updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting inventory with id: {}", id);
        Product entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

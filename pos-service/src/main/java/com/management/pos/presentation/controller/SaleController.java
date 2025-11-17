package com.management.pos.presentation.controller;

import com.management.pos.domain.model.Sale;
import com.management.pos.domain.repository.SaleRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/poss")
@RequiredArgsConstructor
@Slf4j
public class SaleController {
    
    private final SaleRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Sale>> getAll() {
        log.info("Fetching all poss");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Sale> getById(@PathVariable String id) {
        log.info("Fetching pos with id: {}", id);
        Sale entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody Sale entity) {
        log.info("Creating new pos");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Sale saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Sale> update(@PathVariable String id, @RequestBody Sale entity) {
        log.info("Updating pos with id: {}", id);
        Sale existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        Sale updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting pos with id: {}", id);
        Sale entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

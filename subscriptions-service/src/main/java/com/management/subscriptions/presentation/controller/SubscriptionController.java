package com.management.subscriptions.presentation.controller;

import com.management.subscriptions.domain.model.Subscription;
import com.management.subscriptions.domain.repository.SubscriptionRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/subscriptionss")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
    
    private final SubscriptionRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Subscription>> getAll() {
        log.info("Fetching all subscriptionss");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getById(@PathVariable String id) {
        log.info("Fetching subscriptions with id: {}", id);
        Subscription entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<Subscription> create(@RequestBody Subscription entity) {
        log.info("Creating new subscriptions");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Subscription saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Subscription> update(@PathVariable String id, @RequestBody Subscription entity) {
        log.info("Updating subscriptions with id: {}", id);
        Subscription existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        Subscription updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting subscriptions with id: {}", id);
        Subscription entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

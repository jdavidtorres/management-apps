package com.management.planning.presentation.controller;

import com.management.planning.domain.model.Plan;
import com.management.planning.domain.repository.PlanRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/plannings")
@RequiredArgsConstructor
@Slf4j
public class PlanController {
    
    private final PlanRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Plan>> getAll() {
        log.info("Fetching all plannings");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Plan> getById(@PathVariable String id) {
        log.info("Fetching planning with id: {}", id);
        Plan entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plan", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<Plan> create(@RequestBody Plan entity) {
        log.info("Creating new planning");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Plan saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Plan> update(@PathVariable String id, @RequestBody Plan entity) {
        log.info("Updating planning with id: {}", id);
        Plan existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plan", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        Plan updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting planning with id: {}", id);
        Plan entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plan", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

package com.management.helpdesk.presentation.controller;

import com.management.helpdesk.domain.model.Ticket;
import com.management.helpdesk.domain.repository.TicketRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/helpdesks")
@RequiredArgsConstructor
@Slf4j
public class TicketController {
    
    private final TicketRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        log.info("Fetching all helpdesks");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable String id) {
        log.info("Fetching helpdesk with id: {}", id);
        Ticket entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket entity) {
        log.info("Creating new helpdesk");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Ticket saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable String id, @RequestBody Ticket entity) {
        log.info("Updating helpdesk with id: {}", id);
        Ticket existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        Ticket updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting helpdesk with id: {}", id);
        Ticket entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

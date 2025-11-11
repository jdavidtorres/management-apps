package com.management.timesheet.presentation.controller;

import com.management.timesheet.domain.model.TimeEntry;
import com.management.timesheet.domain.repository.TimeEntryRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/timesheets")
@RequiredArgsConstructor
@Slf4j
public class TimeEntryController {
    
    private final TimeEntryRepository repository;
    
    @GetMapping
    public ResponseEntity<List<TimeEntry>> getAll() {
        log.info("Fetching all timesheets");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> getById(@PathVariable String id) {
        log.info("Fetching timesheet with id: {}", id);
        TimeEntry entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TimeEntry", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry entity) {
        log.info("Creating new timesheet");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        TimeEntry saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable String id, @RequestBody TimeEntry entity) {
        log.info("Updating timesheet with id: {}", id);
        TimeEntry existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TimeEntry", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        TimeEntry updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting timesheet with id: {}", id);
        TimeEntry entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TimeEntry", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

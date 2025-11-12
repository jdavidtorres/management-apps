package com.management.projectmgmt.presentation.controller;

import com.management.projectmgmt.domain.model.Project;
import com.management.projectmgmt.domain.repository.ProjectRepository;
import com.management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    
    private final ProjectRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        log.info("Fetching all projects");
        return ResponseEntity.ok(repository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable String id) {
        log.info("Fetching project with id: {}", id);
        Project entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project entity) {
        log.info("Creating new project");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Project saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable String id, @RequestBody Project entity) {
        log.info("Updating project with id: {}", id);
        Project existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        
        entity.setId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        
        Project updated = repository.save(entity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting project with id: {}", id);
        Project entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }
}

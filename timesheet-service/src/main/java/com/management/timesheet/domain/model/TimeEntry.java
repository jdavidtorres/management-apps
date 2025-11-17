package com.management.timesheet.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "timesheets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntry {
    
    @Id
    private String id;
    
    private String name;
    
    private String description;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}

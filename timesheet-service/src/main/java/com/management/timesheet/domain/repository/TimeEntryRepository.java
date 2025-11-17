package com.management.timesheet.domain.repository;

import com.management.timesheet.domain.model.TimeEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeEntryRepository extends MongoRepository<TimeEntry, String> {
    List<TimeEntry> findByName(String name);
}

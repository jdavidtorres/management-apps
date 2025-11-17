package com.management.planning.domain.repository;

import com.management.planning.domain.model.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends MongoRepository<Plan, String> {
    List<Plan> findByName(String name);
}

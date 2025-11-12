package com.management.pos.domain.repository;

import com.management.pos.domain.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends MongoRepository<Sale, String> {
    List<Sale> findByName(String name);
}

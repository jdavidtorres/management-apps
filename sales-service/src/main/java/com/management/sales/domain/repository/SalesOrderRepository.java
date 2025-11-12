package com.management.sales.domain.repository;

import com.management.sales.domain.model.SalesOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderRepository extends MongoRepository<SalesOrder, String> {
    List<SalesOrder> findByName(String name);
}

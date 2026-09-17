package com.restaurant.platform.repository;

import com.restaurant.platform.model.OrderCounter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderCounterRepository
        extends MongoRepository<OrderCounter, String> {
}

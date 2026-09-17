package com.restaurant.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.restaurant.platform.model.Kitchen;

public interface KitchenRepository extends MongoRepository<Kitchen, String> {

    
}
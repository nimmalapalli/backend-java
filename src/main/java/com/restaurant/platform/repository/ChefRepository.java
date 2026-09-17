package com.restaurant.platform.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.restaurant.platform.model.Chef;

public interface ChefRepository
        extends MongoRepository<Chef, String> {

    Optional<Chef> findByKitchenId(String kitchenId);
}

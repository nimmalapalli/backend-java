package com.restaurant.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.restaurant.platform.model.Kiosk;

public interface KioskRepository extends MongoRepository<Kiosk, String> {
}
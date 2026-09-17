package com.restaurant.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.restaurant.platform.model.Customer;
import java.util.*;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByPhone(String phone);
}
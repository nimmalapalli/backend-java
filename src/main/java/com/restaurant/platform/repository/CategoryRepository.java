package com.restaurant.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.restaurant.platform.model.Category;
import java.util.*;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByActiveTrueOrderBySortOrderAsc();
       Optional<Category> findByNameIgnoreCase(String name);
}
package com.restaurant.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.restaurant.platform.model.Product;
import java.util.*;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByOrderBySortOrderAsc();

    List<Product> findByCategoryIdAndAvailableTrueOrderBySortOrderAsc(String categoryId);

        Optional<Product> findByNameIgnoreCase(String name);
}
package com.restaurant.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.restaurant.platform.model.Order;

import java.util.*;

public interface OrderRepository
        extends MongoRepository<Order, String> {
  
    List<Order> findByStatusInOrderByCreatedAtAsc(
            Collection<String> statuses
    );

    long countByCreatedAtGreaterThan(
            java.time.Instant t
    );

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByPaymentStatusOrderByCreatedAtAsc(
            String paymentStatus
    );

       Optional<Order> findByOrderId(String orderId);

    List<Order> findByStatusOrderByCreatedAtAsc(String status);

     



 
}

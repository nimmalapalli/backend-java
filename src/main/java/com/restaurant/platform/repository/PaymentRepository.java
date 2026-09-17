package com.restaurant.platform.repository;

import com.restaurant.platform.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends MongoRepository<Payment, String> {

    /**
     * Find all payments for an order.
     */
    List<Payment> findByOrderId(String orderId);

    /**
     * Find the latest payment for an order.
     */
    Optional<Payment> findFirstByOrderIdOrderByCreatedAtDesc(
            String orderId
    );

    /**
     * Find payments by payment method.
     * Example: CASH, CARD, UPI
     */
    List<Payment> findByMethod(String method);

    /**
     * Find payments by status.
     * Example: SUCCESS, FAILED, PENDING
     */
    List<Payment> findByStatus(String status);

    /**
     * Find a payment using transaction ID.
     */
    Optional<Payment> findByTransactionId(String transactionId);

    /**
     * Find a payment using cash token.
     */
    Optional<Payment> findByCashToken(String cashToken);

    /**
     * Get payments ordered newest first.
     */
    List<Payment> findAllByOrderByCreatedAtDesc();

    /**
     * Get payments for a particular method ordered newest first.
     */
    List<Payment> findByMethodOrderByCreatedAtDesc(String method);

    /**
     * Get successful payments ordered newest first.
     */
    List<Payment> findByStatusOrderByCreatedAtDesc(String status);
}
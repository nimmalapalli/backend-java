package com.restaurant.platform.repository;

import com.restaurant.platform.model.CashPaymentToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CashPaymentTokenRepository
        extends MongoRepository<CashPaymentToken, String> {

    Optional<CashPaymentToken> findByToken(String token);

    Optional<CashPaymentToken> findByOrderId(String orderId);
}

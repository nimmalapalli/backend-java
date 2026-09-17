package com.restaurant.platform.service;

import com.restaurant.platform.dto.CashTokenRequest;
import com.restaurant.platform.dto.CashVerifyRequest;
import com.restaurant.platform.model.CashPaymentToken;
import com.restaurant.platform.model.Payment;
import com.restaurant.platform.model.Order;
import com.restaurant.platform.repository.CashPaymentTokenRepository;
import com.restaurant.platform.repository.PaymentRepository;
import com.restaurant.platform.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository payments;
    private final OrderRepository orders;
    private final CashPaymentTokenRepository cashTokens;

    public PaymentService(
            PaymentRepository payments,
            OrderRepository orders,
            CashPaymentTokenRepository cashTokens) {

        this.payments = payments;
        this.orders = orders;
        this.cashTokens = cashTokens;
    }

public Payment pay(Payment p) {

    System.out.println("========== PAYMENT DEBUG START ==========");

    try {

        // 1. Print received payment
        System.out.println("Order ID: " + p.getOrderId());
        System.out.println("Payment Method: " + p.getMethod());
        System.out.println("Amount: " + p.getAmount());

        // 2. Validate Order ID
        if (p.getOrderId() == null ||
                p.getOrderId().isBlank()) {

            throw new RuntimeException(
                    "Order ID is required"
            );
        }

        System.out.println("Finding order...");

        // 3. Find Order
  Order order = orders.findByOrderId(p.getOrderId())
        .orElseThrow(() ->
                new RuntimeException(
                        "Order not found: "
                                + p.getOrderId()
                )
        );
        System.out.println("Order found successfully");
        System.out.println("MongoDB ID: " + order.getId());
        System.out.println("Business Order ID: " + order.getOrderId());

        // 4. Set Payment Status
        p.setStatus("SUCCESS");

        p.setTransactionId(
                "TXN-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        p.setCreatedAt(Instant.now());

        System.out.println("Saving payment...");

        // 5. Save Payment
        Payment saved = payments.save(p);

        System.out.println("Payment saved successfully");
        System.out.println("Payment ID: " + saved.getId());

        // 6. Update Order
        order.setPaymentMethod(p.getMethod());

        order.setPaymentStatus("PAID");

        order.setStatus("PREPARING");

        order.setUpdatedAt(Instant.now());

        System.out.println("Updating order...");
        System.out.println("Payment Status: " + order.getPaymentStatus());
        System.out.println("Order Status: " + order.getStatus());

        // 7. Save Updated Order
        orders.save(order);

        System.out.println("Order updated successfully");
        System.out.println("========== PAYMENT DEBUG SUCCESS ==========");

        // 8. Return Payment
        return saved;

    } catch (Exception e) {

        System.out.println("========== PAYMENT DEBUG ERROR ==========");

        System.out.println("Error Type: "
                + e.getClass().getName());

        System.out.println("Error Message: "
                + e.getMessage());

        e.printStackTrace();

        System.out.println("========== PAYMENT DEBUG END ==========");

        throw new RuntimeException(
                "Payment failed: " + e.getMessage(),
                e
        );
    }
}
public Payment payCash(String orderId) {

    Order order = orders.findById(orderId)
            .orElseThrow(() ->
                    new RuntimeException("Order not found: " + orderId));

    // Prevent double payment
    if ("PAID".equalsIgnoreCase(order.getPaymentStatus())) {
        throw new RuntimeException("Order is already paid");
    }

    Payment payment = new Payment();

    payment.setOrderId(orderId);
    payment.setMethod("CASH");
    payment.setAmount(order.getTotal());
    payment.setStatus("SUCCESS");

    payment.setTransactionId(
            "CASH-" +
            UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase()
    );

    payment.setCreatedAt(Instant.now());

    Payment savedPayment = payments.save(payment);

    // Update order
    order.setPaymentMethod("CASH");
    order.setPaymentStatus("PAID");
    order.setStatus("PREPARING");
    order.setUpdatedAt(Instant.now());

    orders.save(order);

    return savedPayment;
}

    /**
     * CUSTOMER CREATES CASH TOKEN
     */
public CashPaymentToken createCashToken(CashTokenRequest request) {

    System.out.println("========== CREATE CASH TOKEN ==========");
    System.out.println("Received orderId: " + request.getOrderId());
    System.out.println("Received amount: " + request.getAmount());

    if (request.getOrderId() == null ||
            request.getOrderId().isBlank()) {

        throw new RuntimeException("Order ID is required");
    }

    if (request.getAmount() == null ||
            request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

        throw new RuntimeException("Invalid cash amount");
    }

    Order order = orders.findByOrderId(request.getOrderId())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Order not found: " + request.getOrderId()
                    ));

    System.out.println("Found order:");
    System.out.println("Mongo ID: " + order.getId());
    System.out.println("Business Order ID: " + order.getOrderId());

    CashPaymentToken token = new CashPaymentToken();

    String generatedToken = generateCashToken();

    token.setToken(generatedToken);
    token.setOrderId(order.getOrderId());
    token.setAmount(request.getAmount());
    token.setStatus("PENDING");
    token.setCreatedAt(Instant.now());
    token.setExpiresAt(
            Instant.now().plusSeconds(15 * 60)
    );

    CashPaymentToken saved = cashTokens.save(token);

    System.out.println("========== CASH TOKEN SAVED ==========");
    System.out.println("Token Mongo ID: " + saved.getId());
    System.out.println("Token: " + saved.getToken());
    System.out.println("Token Order ID: " + saved.getOrderId());
    System.out.println("======================================");

    return saved;
}




public Payment verifyCashAndCreateOrder(CashVerifyRequest request) {

    System.out.println();
    System.out.println("=================================================");
    System.out.println("       CASH PAYMENT VERIFICATION START");
    System.out.println("=================================================");

    try {

        // ==========================================
        // 1. VALIDATE REQUEST
        // ==========================================

        System.out.println("[1] Validating request...");

        if (request == null) {
            System.out.println("[ERROR] Request is NULL");
            throw new RuntimeException("Cash token is required");
        }

        if (request.getToken() == null ||
                request.getToken().isBlank()) {

            System.out.println("[ERROR] Token is NULL or EMPTY");

            throw new RuntimeException(
                    "Cash token is required"
            );
        }

        String tokenValue = request.getToken().trim();

        System.out.println(
                "[DEBUG] Token received: " + tokenValue
        );


        // ==========================================
        // 2. FIND CASH TOKEN
        // ==========================================

        System.out.println(
                "[2] Searching cash token in MongoDB..."
        );

        CashPaymentToken token =
                cashTokens.findByToken(tokenValue)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid cash token"
                                ));

        System.out.println(
                "[SUCCESS] Cash token found"
        );

        System.out.println(
                "[DEBUG] Token Mongo ID: " +
                        token.getId()
        );

        System.out.println(
                "[DEBUG] Token Order ID: " +
                        token.getOrderId()
        );

        System.out.println(
                "[DEBUG] Token amount: " +
                        token.getAmount()
        );

        System.out.println(
                "[DEBUG] Token status: " +
                        token.getStatus()
        );


        // ==========================================
        // 3. CHECK TOKEN STATUS
        // ==========================================

        System.out.println(
                "[3] Checking token status..."
        );

        if ("PAID".equalsIgnoreCase(token.getStatus())) {

            throw new RuntimeException(
                    "Cash token already used"
            );
        }

        if ("CANCELLED".equalsIgnoreCase(token.getStatus())) {

            throw new RuntimeException(
                    "Cash token cancelled"
            );
        }

        if ("EXPIRED".equalsIgnoreCase(token.getStatus())) {

            throw new RuntimeException(
                    "Cash token expired"
            );
        }

        System.out.println(
                "[SUCCESS] Token status is valid"
        );


        // ==========================================
        // 4. CHECK EXPIRATION
        // ==========================================

        System.out.println(
                "[4] Checking token expiration..."
        );

        Instant now = Instant.now();

        if (token.getExpiresAt() != null &&
                now.isAfter(token.getExpiresAt())) {

            System.out.println(
                    "[ERROR] Token has expired"
            );

            token.setStatus("EXPIRED");

            cashTokens.save(token);

            throw new RuntimeException(
                    "Cash token expired"
            );
        }

        System.out.println(
                "[SUCCESS] Token has not expired"
        );


        // ==========================================
        // 5. GET ORDER ID
        // ==========================================

        System.out.println(
                "[5] Getting order ID from token..."
        );

        String orderId = token.getOrderId();

        if (orderId == null || orderId.isBlank()) {

            throw new RuntimeException(
                    "Cash token is not linked to an order"
            );
        }

        System.out.println(
                "[DEBUG] Order ID: " + orderId
        );


        // ==========================================
        // 6. FIND ORDER
        // ==========================================

        System.out.println(
                "[6] Searching order..."
        );

        Order order =
                orders.findByOrderId(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found: " +
                                                orderId
                                ));

        System.out.println(
                "[SUCCESS] Order found"
        );

        System.out.println(
                "[DEBUG] Order Mongo ID: " +
                        order.getId()
        );

        System.out.println(
                "[DEBUG] Order business ID: " +
                        order.getOrderId()
        );

        System.out.println(
                "[DEBUG] Order payment status: " +
                        order.getPaymentStatus()
        );


        // ==========================================
        // 7. PREVENT DOUBLE PAYMENT
        // ==========================================

        System.out.println(
                "[7] Checking existing payment status..."
        );

        if ("PAID".equalsIgnoreCase(
                order.getPaymentStatus())) {

            throw new RuntimeException(
                    "Order payment is already completed"
            );
        }

        System.out.println(
                "[SUCCESS] Order is not paid"
        );


        // ==========================================
        // 8. GET PAYMENT AMOUNT FROM TOKEN
        // ==========================================

        System.out.println(
                "[8] Getting payment amount from cash token..."
        );

        BigDecimal amount = token.getAmount();

        System.out.println(
                "[DEBUG] Cash token amount: " + amount
        );


        // ==========================================
        // 9. CREATE PAYMENT
        // ==========================================

        System.out.println(
                "[9] Creating payment..."
        );

        Payment payment = new Payment();

        payment.setOrderId(order.getOrderId());
        payment.setMethod("CASH");
        payment.setAmount(amount);
        payment.setStatus("SUCCESS");

        String transactionId =
                "CASH-" +
                        UUID.randomUUID()
                                .toString()
                                .replace("-", "")
                                .substring(0, 8)
                                .toUpperCase();

        payment.setTransactionId(transactionId);
        payment.setCreatedAt(Instant.now());
        payment.setCashToken(token.getToken());

        System.out.println(
                "[DEBUG] Payment orderId: " +
                        payment.getOrderId()
        );

        System.out.println(
                "[DEBUG] Payment method: " +
                        payment.getMethod()
        );

        System.out.println(
                "[DEBUG] Payment amount: " +
                        payment.getAmount()
        );

        System.out.println(
                "[DEBUG] Payment status: " +
                        payment.getStatus()
        );

        System.out.println(
                "[DEBUG] Transaction ID: " +
                        payment.getTransactionId()
        );


        // ==========================================
        // 10. SAVE PAYMENT
        // ==========================================

        System.out.println(
                "[10] Saving payment to MongoDB..."
        );

        Payment savedPayment =
                payments.save(payment);

        System.out.println(
                "[SUCCESS] Payment saved"
        );

        System.out.println(
                "[DEBUG] Payment Mongo ID: " +
                        savedPayment.getId()
        );


        // ==========================================
        // 11. MARK TOKEN AS PAID
        // ==========================================

        System.out.println(
                "[11] Marking cash token as PAID..."
        );

        token.setStatus("PAID");
        token.setVerifiedAt(Instant.now());

        cashTokens.save(token);

        System.out.println(
                "[SUCCESS] Cash token marked PAID"
        );


        // ==========================================
        // 12. UPDATE ORDER
        // ==========================================

        System.out.println(
                "[12] Updating order..."
        );

        order.setPaymentMethod("CASH");
        order.setPaymentStatus("PAID");
        order.setStatus("PREPARING");
        order.setUpdatedAt(Instant.now());

        orders.save(order);

        System.out.println(
                "[SUCCESS] Order updated"
        );


        // ==========================================
        // 13. SUCCESS
        // ==========================================

        System.out.println();
        System.out.println(
                "================================================="
        );
        System.out.println(
                "       CASH PAYMENT VERIFICATION SUCCESS"
        );
        System.out.println(
                "================================================="
        );

        System.out.println(
                "[RESULT] Payment ID: " +
                        savedPayment.getId()
        );

        System.out.println(
                "[RESULT] Transaction ID: " +
                        savedPayment.getTransactionId()
        );

        System.out.println(
                "[RESULT] Order ID: " +
                        savedPayment.getOrderId()
        );

        System.out.println(
                "[RESULT] Amount: " +
                        savedPayment.getAmount()
        );

        System.out.println(
                "================================================="
        );

        return savedPayment;


    } catch (RuntimeException e) {

        System.out.println();
        System.out.println(
                "================================================="
        );
        System.out.println(
                "       CASH PAYMENT VERIFICATION FAILED"
        );
        System.out.println(
                "================================================="
        );

        System.out.println(
                "[ERROR TYPE] " +
                        e.getClass().getName()
        );

        System.out.println(
                "[ERROR MESSAGE] " +
                        e.getMessage()
        );

        e.printStackTrace();

        System.out.println(
                "================================================="
        );

        throw e;
    }
}




    private String generateCashToken() {

        return "CASH-" +
                (100000 +
                        (int) (Math.random() * 900000));
    }


}
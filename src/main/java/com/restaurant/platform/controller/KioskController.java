package com.restaurant.platform.controller;

import com.restaurant.platform.dto.CashTokenRequest;
import com.restaurant.platform.model.CashPaymentToken;
import com.restaurant.platform.model.Customer;
import com.restaurant.platform.model.Kiosk;
import com.restaurant.platform.model.Order;
import com.restaurant.platform.model.Payment;

import com.restaurant.platform.repository.CustomerRepository;
import com.restaurant.platform.repository.KioskRepository;
import com.restaurant.platform.repository.OrderRepository;

import com.restaurant.platform.service.MenuService;
import com.restaurant.platform.service.OrderService;
import com.restaurant.platform.service.PaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/kiosk")
@CrossOrigin
public class KioskController {

    private final MenuService menu;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final CustomerRepository customers;
    private final KioskRepository kiosks;
    private final OrderRepository orders;

    public KioskController(
            MenuService menu,
            OrderService orderService,
            PaymentService paymentService,
            CustomerRepository customers,
            KioskRepository kiosks,
            OrderRepository orders) {

        this.menu = menu;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.customers = customers;
        this.kiosks = kiosks;
        this.orders = orders;
    }

    // ==============================
    // MENU API
    // ==============================

    @GetMapping("/menu")
    public Map<String, Object> menu(
            @RequestHeader(
                    value = "Accept-Language",
                    defaultValue = "en"
            ) String language) {

        return menu.menu(language);
    }

    // ==============================
    // SETTINGS API
    // ==============================

    @GetMapping("/settings")
    public Object settings() {

        return menu.menu("en").get("restaurant");
    }

    // ==============================
    // CUSTOMER API
    // ==============================

    @PostMapping("/customers")
    public Customer customer(
            @RequestBody Customer customer) {

        return customers.save(customer);
    }

    // ==============================
    // ORDER API
    // ==============================

    @PostMapping("/create-orders")
    public Map<String, String> order(
            @RequestBody Order order) {

        Order savedOrder = orderService.create(order);

        return Map.of(
                "orderId", savedOrder.getOrderId(),
                "pickupNumber", savedOrder.getPickupNumber()
        );
    }

    // ==============================
    // ORDER STATUS API
    // ==============================

    @GetMapping("/orders/{id}/status")
    public Map<String, String> status(
            @PathVariable String id) {

        Order order = orders.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found: " + id
                        ));

        return Map.of(
                "status", order.getStatus(),
                "pickupNumber", order.getPickupNumber()
        );
    }

    // ==============================
    // PAYMENT API
    // ==============================

    @PostMapping("/payments")
    public Payment makePayment(
            @RequestBody Payment payment) {

        return paymentService.pay(payment);
    }

    // ==============================
    // CASH TOKEN API
    // ==============================

    @PostMapping("/payments/cash/token")
    public ResponseEntity<CashPaymentToken> createCashToken(
            @RequestBody CashTokenRequest request) {

        CashPaymentToken token =
                paymentService.createCashToken(request);

        return ResponseEntity.ok(token);
    }

    // ==============================
    // KIOSK HEARTBEAT
    // ==============================

    @PostMapping("/heartbeat")
    public Object heartbeat(
            @RequestParam(
                    defaultValue = "kiosk-1"
            ) String kioskId) {

        Kiosk kiosk = kiosks.findById(kioskId)
                .orElse(
                        Kiosk.builder()
                                .id(kioskId)
                                .name(kioskId)
                                .build()
                );

        kiosk.setStatus("ONLINE");
        kiosk.setLastSeen(Instant.now());

        return kiosks.save(kiosk);
    }

    // ==============================
    // FLAP NUMBER
    // ==============================

    @PostMapping("/flap-number")
    public Map<String, String> generateFlapNumber() {

        String flapNumber = "FLAP-" +
                String.format(
                        "%04d",
                        new Random().nextInt(10000)
                );

        return Map.of(
                "flapNumber",
                flapNumber
        );
    }
}

package com.restaurant.platform.controller;

import com.restaurant.platform.dto.CashVerifyRequest;
import com.restaurant.platform.dto.OrderListResponse;
import com.restaurant.platform.model.Order;
import com.restaurant.platform.model.Payment;
import com.restaurant.platform.repository.OrderRepository;
import com.restaurant.platform.service.OrderService;
import com.restaurant.platform.service.PaymentService;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counter")
@CrossOrigin
public class CounterController {

    private final OrderRepository orders;
    private final PaymentService paymentService;
     private final OrderService orderService;

    public CounterController(
            OrderRepository orders,
            PaymentService paymentService,
             OrderService orderService) {

        this.orders = orders;
        this.paymentService = paymentService;
             this.orderService = orderService;
    }

  @GetMapping("/orders")
public List<OrderListResponse> getOrders() {
   return orderService.getPendingOrders();
}



@PostMapping("/cash/verify")
public ResponseEntity<?> verifyCash(
        @RequestBody CashVerifyRequest request) {

    try {

        Payment payment =
                paymentService.verifyCashAndCreateOrder(request);

        return ResponseEntity.ok(payment);

    } catch (RuntimeException e) {

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());

    } catch (Exception e) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Cash verification failed");
    }
}



    @GetMapping("/{orderId}")
public Order getOrderByOrderId(
        @PathVariable String orderId) {

    return orderService.getOrderByOrderId(
            orderId
    );
}


@GetMapping("/orders/preparing")
public List<Order> getPreparingOrders() {

    return orderService.getPreparingOrders();
}


@GetMapping("/orders/ready")
public List<Order> getReadyOrders() {

    return orderService.getReadyOrders();
}
}

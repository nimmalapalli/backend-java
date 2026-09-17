package com.restaurant.platform.controller;

import com.restaurant.platform.model.Kitchen;
import com.restaurant.platform.model.Order;
import com.restaurant.platform.repository.KitchenRepository;
import com.restaurant.platform.repository.OrderRepository;
import com.restaurant.platform.service.OrderService;
import com.restaurant.platform.model.Chef;
import com.restaurant.platform.repository.ChefRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {

    private final OrderService service;
    private final OrderRepository orders;
    private final KitchenRepository kitchens;
    private final ChefRepository chefs;

    public KitchenController(
            OrderService service,
            OrderRepository orders,
            KitchenRepository kitchens,
        ChefRepository chefs) {

        this.service = service;
        this.orders = orders;
        this.kitchens = kitchens;
         this.chefs = chefs;
    }

    // =========================================================
    // KITCHENS
    // =========================================================

    @GetMapping("/kitchens")
    public List<Kitchen> getAllKitchens() {

        return kitchens.findAll();
    }
// =========================================================
// CHEF MANAGEMENT
// =========================================================

@PostMapping("/kitchens/{kitchenId}/chef")
public Chef addChef(
        @PathVariable String kitchenId,
        @RequestBody Chef chef) {

    // Check kitchen exists
    kitchens.findById(kitchenId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Kitchen not found: "
                                    + kitchenId
                    )
            );

    // Assign kitchen ID to chef
    chef.setKitchenId(kitchenId);

    return chefs.save(chef);
}
@GetMapping("/kitchens/{id}")
public Map<String, Object> getKitchenById(
        @PathVariable String id) {

    Kitchen kitchen = kitchens.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Kitchen not found: " + id
                    )
            );

    Chef chef = chefs.findByKitchenId(id)
            .orElse(null);

    Map<String, Object> response = new LinkedHashMap<>();

    response.put("kitchen", kitchen);
    response.put("chef", chef);

    return response;
}
    @PostMapping("/kitchens")
    public Kitchen createKitchen(
            @RequestBody Kitchen kitchen) {

        if (kitchen.getCategoryIds() == null) {
            kitchen.setCategoryIds(
                    new ArrayList<>()
            );
        }

        if (kitchen.getStations() == null) {
            kitchen.setStations(
                    new ArrayList<>()
            );
        }

        if (kitchen.getStatus() == null ||
                kitchen.getStatus().isBlank()) {

            kitchen.setStatus("OFFLINE");
        }

        if (kitchen.getAutoRefreshSeconds() == null) {
            kitchen.setAutoRefreshSeconds(5);
        }

        if (kitchen.getSoundEnabled() == null) {
            kitchen.setSoundEnabled(true);
        }

        return kitchens.save(kitchen);
    }

    @PutMapping("/kitchens/{id}")
    public Kitchen updateKitchen(
            @PathVariable String id,
            @RequestBody Kitchen kitchen) {

        Kitchen existing =
                kitchens.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Kitchen not found: "
                                                + id
                                )
                        );

        if (kitchen.getName() != null) {
            existing.setName(
                    kitchen.getName()
            );
        }

        if (kitchen.getStatus() != null) {
            existing.setStatus(
                    kitchen.getStatus()
            );
        }

        if (kitchen.getStations() != null) {
            existing.setStations(
                    kitchen.getStations()
            );
        }

        if (kitchen.getCategoryIds() != null) {
            existing.setCategoryIds(
                    kitchen.getCategoryIds()
            );
        }

        if (kitchen.getAutoRefreshSeconds() != null) {
            existing.setAutoRefreshSeconds(
                    kitchen.getAutoRefreshSeconds()
            );
        }

        if (kitchen.getSoundEnabled() != null) {
            existing.setSoundEnabled(
                    kitchen.getSoundEnabled()
            );
        }

        return kitchens.save(existing);
    }

    @DeleteMapping("/kitchens/{id}")
    public void deleteKitchen(
            @PathVariable String id) {

        if (!kitchens.existsById(id)) {

            throw new RuntimeException(
                    "Kitchen not found: "
                            + id
            );
        }

        kitchens.deleteById(id);
    }

    // =========================================================
    // CATEGORY CONFIGURATION
    // =========================================================

    @GetMapping("/kitchens/{id}/categories")
    public List<String> getKitchenCategories(
            @PathVariable String id) {

        Kitchen kitchen =
                kitchens.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Kitchen not found: "
                                                + id
                                )
                        );

        return kitchen.getCategoryIds() == null
                ? new ArrayList<>()
                : kitchen.getCategoryIds();
    }

    @PutMapping("/kitchens/{id}/categories")
    public Kitchen updateKitchenCategories(
            @PathVariable String id,
            @RequestBody List<String> categoryIds) {

        Kitchen kitchen =
                kitchens.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Kitchen not found: "
                                                + id
                                )
                        );

        kitchen.setCategoryIds(
                categoryIds == null
                        ? new ArrayList<>()
                        : categoryIds
        );

        return kitchens.save(kitchen);
    }



@GetMapping("/kitchen-all-orders")
public ResponseEntity<List<Order>> kitchenAllOrders(
        @RequestParam("kitchenId") String kitchenId) {

    return ResponseEntity.ok(
            service.kitchen(kitchenId)
    );
}

 

    @GetMapping("/orders/{id}")
    public Order order(
            @PathVariable String id) {

        return orders.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found: "
                                        + id
                        )
                );
    }



    @PutMapping(
            "/orders/{orderId}/items/{productId}/status"
    )
    public Order updateItemStatus(
            @PathVariable String orderId,
            @PathVariable String productId,
            @RequestParam String kitchenId,
            @RequestBody Map<String, String> body) {

        String status =
                body.get("status");

        return service.updateKitchenItemStatus(
                orderId,
                productId,
                kitchenId,
                status
        );
    }

    // =========================================================
    // LEGACY WHOLE ORDER STATUS
    // =========================================================

    @PutMapping("/orders/{id}/status")
    public Order status(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {

        return service.status(
                id,
                body.get("status")
        );
    }

    // =========================================================
    // SETTINGS
    // =========================================================

    @GetMapping("/settings")
    public Kitchen settings() {

        return kitchens.findAll()
                .stream()
                .findFirst()
                .orElse(
                        Kitchen.builder()
                                .id("kitchen-1")
                                .name("Main Kitchen")
                                .autoRefreshSeconds(5)
                                .soundEnabled(true)
                                .build()
                );
    }

    // =========================================================
    // HEARTBEAT
    // =========================================================

    @PostMapping("/heartbeat")
    public Kitchen heartbeat() {

        Kitchen kitchen = settings();

        kitchen.setStatus("ONLINE");

        return kitchens.save(kitchen);
    }
}
package com.restaurant.platform.controller;

import org.springframework.web.bind.annotation.*;

import com.restaurant.platform.repository.*;
import com.restaurant.platform.model.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CategoryRepository categories;
    private final ProductRepository products;
    private final ModifierRepository modifiers;
    private final OfferRepository offers;
    private final OrderRepository orders;
    private final KioskRepository kiosks;
    private final KitchenRepository kitchens;
    private final SettingsRepository settings;
   private final ChefRepository chefs;
    public AdminController(
            CategoryRepository categories,
            ProductRepository products,
            ModifierRepository modifiers,
            OfferRepository offers,
            OrderRepository orders,
            KioskRepository kiosks,
            KitchenRepository kitchens,
            SettingsRepository settings,
         ChefRepository chefs
        ) {

        this.categories = categories;
        this.products = products;
        this.modifiers = modifiers;
        this.offers = offers;
        this.orders = orders;
        this.kiosks = kiosks;
        this.kitchens = kitchens;
        this.settings = settings;
        this.chefs = chefs;
    }

    // =========================================================
    // CATEGORIES
    // =========================================================

    @GetMapping("/categories")
    public List<Category> categories() {
        return categories.findAll();
    }

    @PostMapping("/categories")
    public Category createCategory(
            @RequestBody Category x) {

        return categories.save(x);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(
            @PathVariable String id,
            @RequestBody Category x) {

        if (!categories.existsById(id)) {
            throw new RuntimeException(
                    "Category not found: " + id
            );
        }

        x.setId(id);

        return categories.save(x);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(
            @PathVariable String id) {

        if (!categories.existsById(id)) {
            throw new RuntimeException(
                    "Category not found: " + id
            );
        }

        categories.deleteById(id);
    }

    // =========================================================
    // PRODUCTS
    // =========================================================

    @GetMapping("/products")
    public List<Product> products() {
        return products.findAllByOrderBySortOrderAsc();
    }

    @PostMapping("/products")
    public Product createProduct(
            @RequestBody Product x) {

        return products.save(x);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(
            @PathVariable String id,
            @RequestBody Product x) {

        if (!products.existsById(id)) {
            throw new RuntimeException(
                    "Product not found: " + id
            );
        }

        x.setId(id);

        return products.save(x);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(
            @PathVariable String id) {

        if (!products.existsById(id)) {
            throw new RuntimeException(
                    "Product not found: " + id
            );
        }

        products.deleteById(id);
    }

    // =========================================================
    // MODIFIERS
    // =========================================================

    @GetMapping("/modifiers")
    public List<Modifier> modifiers() {
        return modifiers.findAll();
    }

    @PostMapping("/modifiers")
    public Modifier createModifier(
            @RequestBody Modifier x) {

        return modifiers.save(x);
    }

    @PutMapping("/modifiers/{id}")
    public Modifier updateModifier(
            @PathVariable String id,
            @RequestBody Modifier x) {

        if (!modifiers.existsById(id)) {
            throw new RuntimeException(
                    "Modifier not found: " + id
            );
        }

        x.setId(id);

        return modifiers.save(x);
    }

    @DeleteMapping("/modifiers/{id}")
    public void deleteModifier(
            @PathVariable String id) {

        if (!modifiers.existsById(id)) {
            throw new RuntimeException(
                    "Modifier not found: " + id
            );
        }

        modifiers.deleteById(id);
    }

    // =========================================================
    // OFFERS
    // =========================================================

    @GetMapping("/offers")
    public List<Offer> offers() {
        return offers.findAll();
    }

    @PostMapping("/offers")
    public Offer createOffer(
            @RequestBody Offer x) {

        return offers.save(x);
    }

    @PutMapping("/offers/{id}")
    public Offer updateOffer(
            @PathVariable String id,
            @RequestBody Offer x) {

        if (!offers.existsById(id)) {
            throw new RuntimeException(
                    "Offer not found: " + id
            );
        }

        x.setId(id);

        return offers.save(x);
    }

    @DeleteMapping("/offers/{id}")
    public void deleteOffer(
            @PathVariable String id) {

        if (!offers.existsById(id)) {
            throw new RuntimeException(
                    "Offer not found: " + id
            );
        }

        offers.deleteById(id);
    }

    // =========================================================
    // ORDERS
    // =========================================================

    @PostMapping("/orders")
    public Order createOrder(
            @RequestBody Order order) {

        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        return orders.save(order);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orders.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/orders/{id}")
    public Order getOrder(
            @PathVariable String id) {

        return orders.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found: " + id
                        )
                );
    }

    @PutMapping("/orders/{id}")
    public Order updateOrder(
            @PathVariable String id,
            @RequestBody Order order) {

        if (!orders.existsById(id)) {
            throw new RuntimeException(
                    "Order not found: " + id
            );
        }

        order.setId(id);
        order.setUpdatedAt(Instant.now());

        return orders.save(order);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrder(
            @PathVariable String id) {

        if (!orders.existsById(id)) {
            throw new RuntimeException(
                    "Order not found: " + id
            );
        }

        orders.deleteById(id);
    }

    // =========================================================
    // KIOSKS
    // =========================================================

    @PostMapping("/kiosks")
    public Kiosk createKiosk(
            @RequestBody Kiosk kiosk) {

        return kiosks.save(kiosk);
    }

    @GetMapping("/kiosks")
    public List<Kiosk> getAllKiosks() {
        return kiosks.findAll();
    }

    @GetMapping("/kiosks/{id}")
    public Kiosk getKiosk(
            @PathVariable String id) {

        return kiosks.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Kiosk not found: " + id
                        )
                );
    }

    @PutMapping("/kiosks/{id}")
    public Kiosk updateKiosk(
            @PathVariable String id,
            @RequestBody Kiosk kiosk) {

        if (!kiosks.existsById(id)) {
            throw new RuntimeException(
                    "Kiosk not found: " + id
            );
        }

        kiosk.setId(id);

        return kiosks.save(kiosk);
    }

    @DeleteMapping("/kiosks/{id}")
    public void deleteKiosk(
            @PathVariable String id) {

        if (!kiosks.existsById(id)) {
            throw new RuntimeException(
                    "Kiosk not found: " + id
            );
        }

        kiosks.deleteById(id);
    }



@PostMapping("/kitchens")
public Kitchen createKitchen(
        @RequestBody Kitchen kitchen) {

    // Default values
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

// =========================================================
// KDS / KITCHENS
// =========================================================

@PostMapping("/kitchens/generate-from-categories")
public List<Kitchen> generateKitchensFromCategories() {

    List<Category> allCategories = categories.findAll();
    List<Kitchen> allKitchens = kitchens.findAll();

    List<Kitchen> createdKitchens = new ArrayList<>();

    for (Category category : allCategories) {

        if (category.getId() == null ||
                category.getId().isBlank()) {
            continue;
        }

        boolean exists = allKitchens.stream()
                .anyMatch(kitchen ->
                        kitchen.getCategoryIds() != null
                                && kitchen.getCategoryIds().size() == 1
                                && kitchen.getCategoryIds()
                                           .contains(category.getId())
                );

        if (exists) {
            continue;
        }

        Kitchen kitchen = Kitchen.builder()
                .name(category.getName() + " KDS")
                .categoryIds(
                        new ArrayList<>(
                                List.of(category.getId())
                        )
                )
                .stations(new ArrayList<>())
                .status("OFFLINE")
                .autoRefreshSeconds(5)
                .soundEnabled(true)
                .build();

        Kitchen saved = kitchens.save(kitchen);

        createdKitchens.add(saved);
        allKitchens.add(saved);
    }

    return createdKitchens;
}

@PutMapping("/kitchens/{id}/categories")
public Kitchen updateKitchenCategories(
        @PathVariable String id,
        @RequestBody List<String> categoryIds) {

    Kitchen kitchen = kitchens.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Kitchen not found: " + id
                    )
            );

    kitchen.setCategoryIds(
            categoryIds == null
                    ? new ArrayList<>()
                    : new ArrayList<>(categoryIds)
    );

    return kitchens.save(kitchen);
}

@GetMapping("/kitchens/{id}/categories")
public List<String> getKitchenCategories(
        @PathVariable String id) {

    Kitchen kitchen = kitchens.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Kitchen not found: " + id
                    )
            );

    return kitchen.getCategoryIds() == null
            ? new ArrayList<>()
            : kitchen.getCategoryIds();
}
@GetMapping("/kitchens")
public List<Kitchen> getAllKitchens() {

    return kitchens.findAll();
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


@PutMapping("/kitchens/{id}")
public Kitchen updateKitchen(
        @PathVariable String id,
        @RequestBody Kitchen kitchen) {

    Kitchen existing = kitchens.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Kitchen not found: " + id
                    )
            );

    // -----------------------------------------
    // BASIC INFORMATION
    // -----------------------------------------

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

    // -----------------------------------------
    // STATIONS
    // -----------------------------------------

    if (kitchen.getStations() != null) {
        existing.setStations(
                kitchen.getStations()
        );
    }

    // -----------------------------------------
    // CATEGORY ASSIGNMENT
    // -----------------------------------------

    if (kitchen.getCategoryIds() != null) {
        existing.setCategoryIds(
                kitchen.getCategoryIds()
        );
    }

    // -----------------------------------------
    // DISPLAY SETTINGS
    // -----------------------------------------

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
                "Kitchen not found: " + id
        );
    }

    kitchens.deleteById(id);
}
 
    // =========================================================
    // SETTINGS
    // =========================================================

    @PostMapping("/settings")
    public RestaurantSettings createSettings(
            @RequestBody RestaurantSettings settings) {

        return this.settings.save(settings);
    }

    @GetMapping("/settings")
    public List<RestaurantSettings> getAllSettings() {
        return settings.findAll();
    }

    @GetMapping("/settings/{id}")
    public RestaurantSettings getSettingsById(
            @PathVariable String id) {

        return settings.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Settings not found: " + id
                        )
                );
    }

    @DeleteMapping("/settings/{id}")
    public void deleteSettings(
            @PathVariable String id) {

        if (!settings.existsById(id)) {
            throw new RuntimeException(
                    "Settings not found: " + id
            );
        }

        settings.deleteById(id);
    }

    // =========================================================
    // DASHBOARD
    // =========================================================

    @GetMapping("/dashboard")
    public List<Map<String, Object>> dashboard() {

        Map<String, Object> x =
                new LinkedHashMap<>();

        BigDecimal revenue =
                orders.findAll()
                        .stream()
                        .map(Order::getTotal)
                        .filter(Objects::nonNull)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        x.put("orders", orders.count());
        x.put("revenue", revenue);
        x.put("products", products.count());
        x.put("kiosks", kiosks.count());
        x.put("kitchens", kitchens.count());

        return List.of(x);
    }
}

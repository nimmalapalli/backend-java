
package com.restaurant.platform.service;

import org.springframework.stereotype.Service;

import com.restaurant.platform.dto.OrderListResponse;
import com.restaurant.platform.model.*;
import com.restaurant.platform.repository.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orders;
    private final ProductRepository products;
    private final ModifierRepository modifiers;
    private final OrderCounterRepository orderCounters;
    private final CashPaymentTokenRepository cashTokens;
    private final KitchenRepository kitchens;

    public OrderService(
            OrderRepository orders,
            ProductRepository products,
            ModifierRepository modifiers,
            OrderCounterRepository orderCounters,
            CashPaymentTokenRepository cashTokens,
            KitchenRepository kitchens) {

        this.orders = orders;
        this.products = products;
        this.modifiers = modifiers;
        this.orderCounters = orderCounters;
        this.cashTokens = cashTokens;
        this.kitchens = kitchens;
    }

    // =========================================================
    // CHECK WHETHER ITEM BELONGS TO KITCHEN
    // =========================================================

    private boolean itemBelongsToKitchen(
            Order.Item item,
            Set<String> kitchenCategoryIds) {

        if (item == null ||
                kitchenCategoryIds == null ||
                kitchenCategoryIds.isEmpty()) {

            return false;
        }

        Set<String> normalizedKitchenCategoryIds =
                kitchenCategoryIds.stream()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(id -> !id.isEmpty())
                        .collect(Collectors.toSet());

        // =========================================================
        // 1. FIRST: CATEGORY ID STORED IN ORDER ITEM
        // =========================================================

        String itemCategoryId = item.getCategoryId();

        if (itemCategoryId != null &&
                !itemCategoryId.isBlank()) {

            String normalizedItemCategoryId =
                    itemCategoryId.trim();

            boolean matched =
                    normalizedKitchenCategoryIds.contains(
                            normalizedItemCategoryId
                    );

            System.out.println(
                    "ITEM CATEGORY CHECK"
                            + " | Product="
                            + item.getName()
                            + " | ProductId="
                            + item.getProductId()
                            + " | ItemCategory="
                            + normalizedItemCategoryId
                            + " | KitchenCategories="
                            + normalizedKitchenCategoryIds
                            + " | MATCH="
                            + matched
            );

            return matched;
        }

        // =========================================================
        // 2. FALLBACK: FIND PRODUCT USING PRODUCT ID
        // =========================================================

        String productId = item.getProductId();

        if (productId == null || productId.isBlank()) {

            System.out.println(
                    "NO PRODUCT ID FOR ITEM: "
                            + item.getName()
            );

            return false;
        }

        return products.findById(productId)
                .map(product -> {

                    String productCategoryId =
                            product.getCategoryId();

                    if (productCategoryId == null ||
                            productCategoryId.isBlank()) {

                        System.out.println(
                                "PRODUCT HAS NO CATEGORY"
                                        + " | Product="
                                        + product.getName()
                                        + " | ProductId="
                                        + productId
                        );

                        return false;
                    }

                    String normalizedProductCategoryId =
                            productCategoryId.trim();

                    boolean matched =
                            normalizedKitchenCategoryIds.contains(
                                    normalizedProductCategoryId
                            );

                    System.out.println(
                            "PRODUCT CATEGORY CHECK"
                                    + " | Product="
                                    + product.getName()
                                    + " | ProductId="
                                    + productId
                                    + " | ProductCategory="
                                    + normalizedProductCategoryId
                                    + " | KitchenCategories="
                                    + normalizedKitchenCategoryIds
                                    + " | MATCH="
                                    + matched
                    );

                    return matched;

                })
                .orElseGet(() -> {

                    System.out.println(
                            "PRODUCT NOT FOUND"
                                    + " | ProductId="
                                    + productId
                    );

                    return false;
                });
    }

    // =========================================================
    // COPY ORDER
    // =========================================================

    private Order copyOrder(Order source) {

        if (source == null) {
            return null;
        }

        Order target = new Order();

        target.setId(source.getId());
        target.setOrderId(source.getOrderId());
        target.setKioskId(source.getKioskId());
        target.setPickupNumber(source.getPickupNumber());
        target.setOrderType(source.getOrderType());
        target.setStatus(source.getStatus());

        target.setPaymentMethod(source.getPaymentMethod());
        target.setPaymentStatus(source.getPaymentStatus());
        target.setCustomerId(source.getCustomerId());

        target.setSubtotal(source.getSubtotal());
        target.setTax(source.getTax());
        target.setDiscount(source.getDiscount());
        target.setTotal(source.getTotal());

        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());
        target.setPriority(source.getPriority());

        return target;
    }

    // =========================================================
    // FILTER ORDER FOR KITCHEN
    // =========================================================

    private Order filterOrderForKitchen(
            Order order,
            Set<String> kitchenCategoryIds) {

        if (order == null
                || order.getItems() == null
                || order.getItems().isEmpty()
                || kitchenCategoryIds == null
                || kitchenCategoryIds.isEmpty()) {

            return null;
        }

        List<Order.Item> kitchenItems =
                order.getItems()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(item ->
                                itemBelongsToKitchen(
                                        item,
                                        kitchenCategoryIds
                                )
                        )
                        .toList();

        if (kitchenItems.isEmpty()) {
            return null;
        }

        Order kitchenOrder =
                copyOrder(order);

        kitchenOrder.setItems(kitchenItems);

        return kitchenOrder;
    }

    // =========================================================
    // KITCHEN ORDERS BY KITCHEN ID
    // ONLY PREPARING AND READY
    // =========================================================

    public List<Order> kitchen(String kitchenId) {

        // =========================================================
        // 1. FIND KITCHEN
        // =========================================================

        Kitchen kitchen = kitchens.findById(kitchenId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Kitchen not found: " + kitchenId
                        )
                );

        List<String> categoryIds =
                kitchen.getCategoryIds();

        System.out.println("====================================");
        System.out.println("KITCHEN");
        System.out.println("====================================");
        System.out.println("Kitchen ID   : " + kitchenId);
        System.out.println("Kitchen Name : " + kitchen.getName());
        System.out.println("Raw Categories: " + categoryIds);
        System.out.println("====================================");

        // =========================================================
        // 2. NORMALIZE CATEGORY IDS
        // =========================================================

        Set<String> kitchenCategoryIds =
                categoryIds == null
                        ? Collections.emptySet()
                        : categoryIds.stream()
                                .filter(Objects::nonNull)
                                .map(String::trim)
                                .filter(id -> !id.isEmpty())
                                .collect(Collectors.toSet());

        System.out.println(
                "NORMALIZED KITCHEN CATEGORIES: "
                        + kitchenCategoryIds
        );

        // =========================================================
        // 3. GET ACTIVE ORDERS
        // ONLY PREPARING AND READY
        // =========================================================

        List<Order> activeOrders =
                orders.findByStatusInOrderByCreatedAtAsc(
                        List.of(
                                "PREPARING",
                                "READY"
                        )
                );

        System.out.println(
                "ACTIVE ORDERS: "
                        + activeOrders.size()
        );

        // =========================================================
        // 4. FILTER ORDERS
        // =========================================================

        return activeOrders.stream()

                .map(order -> {

                    if (order.getItems() == null ||
                            order.getItems().isEmpty()) {

                        return null;
                    }

                    List<Order.Item> matchingItems =
                            order.getItems()
                                    .stream()
                                    .filter(Objects::nonNull)
                                    .filter(item ->
                                            itemBelongsToKitchen(
                                                    item,
                                                    kitchenCategoryIds
                                            )
                                    )
                                    .collect(Collectors.toList());

                    // No items for this kitchen
                    if (matchingItems.isEmpty()) {

                        System.out.println(
                                "NO MATCH"
                                        + " | Order="
                                        + order.getOrderId()
                                        + " | Kitchen="
                                        + kitchenId
                        );

                        return null;
                    }

                    // =================================================
                    // CREATE KITCHEN ORDER
                    // =================================================

                    Order filteredOrder =
                            copyOrder(order);

                    filteredOrder.setItems(
                            new ArrayList<>(matchingItems)
                    );

                    System.out.println(
                            "MATCHED ORDER"
                                    + " | Order="
                                    + order.getOrderId()
                                    + " | Kitchen="
                                    + kitchen.getName()
                                    + " | Items="
                                    + matchingItems.size()
                    );

                    return filteredOrder;
                })

                .filter(Objects::nonNull)

                .collect(Collectors.toList());
    }

    // =========================================================
    // UPDATE KITCHEN ITEM STATUS
    //
    // ONLY:
    // PREPARING
    // READY
    // =========================================================

    public Order updateKitchenItemStatus(
            String orderId,
            String productId,
            String kitchenId,
            String status) {

        System.out.println("========================================");
        System.out.println("KITCHEN ITEM STATUS UPDATE");
        System.out.println("orderId   = [" + orderId + "]");
        System.out.println("productId = [" + productId + "]");
        System.out.println("kitchenId = [" + kitchenId + "]");
        System.out.println("status    = [" + status + "]");
        System.out.println("========================================");

        // =========================================================
        // 1. FIND KITCHEN
        // =========================================================

        Kitchen kitchen = kitchens.findById(kitchenId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Kitchen not found: " + kitchenId
                        )
                );

        Set<String> categoryIds =
                kitchen.getCategoryIds() == null
                        ? Collections.emptySet()
                        : kitchen.getCategoryIds()
                                .stream()
                                .filter(Objects::nonNull)
                                .map(String::trim)
                                .filter(id -> !id.isEmpty())
                                .collect(Collectors.toSet());

        System.out.println(
                "Kitchen categoryIds = "
                        + categoryIds
        );

        // =========================================================
        // 2. FIND ORDER USING orderId
        // =========================================================

        Optional<Order> orderOptional =
                orders.findByOrderId(orderId);

        // =========================================================
        // 3. FALLBACK TO MONGO _id
        // =========================================================

        if (orderOptional.isEmpty()) {

            System.out.println(
                    "ORDER NOT FOUND USING orderId = ["
                            + orderId
                            + "]"
            );

            System.out.println(
                    "Trying Mongo document id lookup..."
            );

            Optional<Order> byId;

            try {
                byId = orders.findByOrderId(orderId);
            } catch (Exception ex) {
                byId = Optional.empty();
            }

            if (byId.isPresent()) {

                System.out.println(
                        "FOUND ORDER USING Mongo _id = ["
                                + orderId
                                + "]"
                );

                orderOptional = byId;

            } else {

                throw new RuntimeException(
                        "Order not found. Neither orderId nor Mongo _id matches: "
                                + orderId
                );
            }
        }

        // =========================================================
        // 4. GET ORDER
        // =========================================================

        Order order =
                orderOptional.get();

        System.out.println(
                "FOUND ORDER:"
                        + " mongoId=" + order.getId()
                        + ", orderId=" + order.getOrderId()
        );

        // =========================================================
        // 5. CHECK ORDER ITEMS
        // =========================================================

        if (order.getItems() == null ||
                order.getItems().isEmpty()) {

            throw new RuntimeException(
                    "Order has no items: "
                            + orderId
            );
        }

        // =========================================================
        // 6. FIND PRODUCT ITEM
        // =========================================================

        Order.Item item =
                order.getItems()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(i ->
                                Objects.equals(
                                        i.getProductId(),
                                        productId
                                )
                        )
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found in order. "
                                                + "orderId="
                                                + orderId
                                                + ", productId="
                                                + productId
                                )
                        );

        System.out.println(
                "FOUND ITEM:"
                        + " productId=" + item.getProductId()
                        + ", name=" + item.getName()
                        + ", categoryId=" + item.getCategoryId()
                        + ", currentStatus=" + item.getKitchenStatus()
        );

        // =========================================================
        // 7. VERIFY ITEM BELONGS TO KITCHEN
        // =========================================================

        if (!itemBelongsToKitchen(
                item,
                categoryIds)) {

            throw new RuntimeException(
                    "Item does not belong to kitchen. "
                            + "kitchenId=" + kitchenId
                            + ", productId=" + productId
                            + ", categoryId=" + item.getCategoryId()
            );
        }

        // =========================================================
        // 8. VALIDATE STATUS
        // ONLY PREPARING / READY
        // =========================================================

        validateKitchenStatus(status);

        String normalizedStatus =
                status.trim().toUpperCase();

        // =========================================================
        // 9. UPDATE ITEM STATUS
        // =========================================================

        item.setKitchenStatus(
                normalizedStatus
        );

        // =========================================================
        // 10. UPDATE OVERALL ORDER STATUS
        // =========================================================

        updateOverallOrderStatus(order);

        // =========================================================
        // 11. UPDATE TIMESTAMP
        // =========================================================

        order.setUpdatedAt(
                Instant.now()
        );

        // =========================================================
        // 12. SAVE
        // =========================================================

        Order savedOrder =
                orders.save(order);

        System.out.println(
                "KITCHEN ITEM STATUS UPDATED SUCCESSFULLY"
                        + " orderId="
                        + order.getOrderId()
                        + ", productId="
                        + productId
                        + ", status="
                        + normalizedStatus
                        + ", overallOrderStatus="
                        + order.getStatus()
        );

        return savedOrder;
    }

    // =========================================================
    // VALIDATE KITCHEN STATUS
    // ONLY PREPARING AND READY
    // =========================================================

    private void validateKitchenStatus(
            String status) {

        if (status == null ||
                status.isBlank()) {

            throw new RuntimeException(
                    "Kitchen status is required"
            );
        }

        String normalized =
                status.trim().toUpperCase();

        if (!Set.of(
                "PREPARING",
                "READY"
        ).contains(normalized)) {

            throw new RuntimeException(
                    "Invalid kitchen status: "
                            + status
                            + ". Allowed: PREPARING, READY"
            );
        }
    }

    // =========================================================
    // UPDATE OVERALL ORDER STATUS
    //
    // ANY ITEM NOT READY -> PREPARING
    // ALL ITEMS READY    -> READY
    // =========================================================

    private void updateOverallOrderStatus(
            Order order) {

        if (order == null ||
                order.getItems() == null ||
                order.getItems().isEmpty()) {

            return;
        }

        boolean allReady =
                order.getItems()
                        .stream()
                        .filter(Objects::nonNull)
                        .allMatch(item ->
                                "READY".equalsIgnoreCase(
                                        item.getKitchenStatus()
                                )
                        );

        if (allReady) {

            order.setStatus("READY");

        } else {

            order.setStatus("PREPARING");
        }

        order.setUpdatedAt(
                Instant.now()
        );
    }

    // =========================================================
    // GET PENDING PAYMENT ORDERS
    // =========================================================

    public List<OrderListResponse> getPendingOrders() {

        List<Order> pendingOrders =
                orders.findByPaymentStatusOrderByCreatedAtAsc(
                        "PENDING"
                );

        return pendingOrders.stream()
                .map(order -> {

                    String tokenNumber = null;

                    if ("CASH".equalsIgnoreCase(
                            order.getPaymentMethod())) {

                        tokenNumber =
                                cashTokens
                                        .findByOrderId(
                                                order.getOrderId()
                                        )
                                        .map(
                                                CashPaymentToken::getToken
                                        )
                                        .orElse(null);
                    }

                    return new OrderListResponse(
                            order,
                            tokenNumber
                    );
                })
                .toList();
    }

    // =========================================================
    // CREATE ORDER
    //
    // NEW ORDERS START DIRECTLY AS PREPARING
    // =========================================================

    public Order create(Order o) {

        if (o.getOrderId() == null ||
                o.getOrderId().isBlank()) {

            o.setOrderId(
                    generateOrderId(
                            o.getKioskId()
                    )
            );
        }

        // =========================================================
        // ONLY PREPARING / READY ARE USED
        // =========================================================

        o.setStatus("PREPARING");

        o.setPaymentStatus("PENDING");

        o.setCreatedAt(
                Instant.now()
        );

        o.setUpdatedAt(
                Instant.now()
        );

        if (o.getPickupNumber() == null ||
                o.getPickupNumber().isBlank()) {

            o.setPickupNumber(
                    String.format(
                            "%03d",
                            (orders.findAll().size() % 999) + 1
                    )
            );
        }

        calculate(o);

        return orders.save(o);
    }

    // =========================================================
    // GENERATE ORDER ID
    // =========================================================

    private String generateOrderId(
            String kioskId) {

        String actualKioskId =
                (kioskId == null ||
                        kioskId.isBlank())
                        ? "KIOSK"
                        : kioskId;

        String yearMonth =
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyyMM"
                                )
                        );

        String counterId =
                actualKioskId
                        + "-"
                        + yearMonth;

        OrderCounter counter =
                orderCounters
                        .findById(counterId)
                        .orElse(null);

        if (counter == null) {

            counter = new OrderCounter();

            counter.setId(counterId);
            counter.setKioskId(actualKioskId);
            counter.setYearMonth(yearMonth);
            counter.setSequence(0);
        }

        counter.setSequence(
                counter.getSequence() + 1
        );

        orderCounters.save(counter);

        return String.format(
                "%s-%s-%04d",
                actualKioskId,
                yearMonth,
                counter.getSequence()
        );
    }

    // =========================================================
    // CALCULATE ORDER
    // =========================================================

    public void calculate(Order o) {

        BigDecimal sub =
                BigDecimal.ZERO;

        if (o.getItems() == null) {

            o.setItems(
                    new ArrayList<>()
            );
        }

        for (Order.Item i : o.getItems()) {

            // =====================================================
            // DEFAULT QUANTITY
            // =====================================================

            if (i.getQuantity() == null ||
                    i.getQuantity() <= 0) {

                i.setQuantity(1);
            }

            // =====================================================
            // GET PRODUCT PRICE
            // =====================================================

            if (i.getUnitPrice() == null) {

                products.findById(
                        i.getProductId()
                ).ifPresent(
                        p -> i.setUnitPrice(
                                p.getPrice()
                        )
                );
            }

            // =====================================================
            // PREVENT NULL PRICE
            // =====================================================

            if (i.getUnitPrice() == null) {

                i.setUnitPrice(
                        BigDecimal.ZERO
                );
            }

            // =====================================================
            // PRODUCT LINE TOTAL
            // =====================================================

            BigDecimal line =
                    i.getUnitPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            i.getQuantity()
                                    )
                            );

            // =====================================================
            // ADD MODIFIERS
            // =====================================================

            if (i.getModifiers() != null) {

                for (Order.SelectedModifier x :
                        i.getModifiers()) {

                    if (x.getPrice() != null) {

                        BigDecimal modifierTotal =
                                x.getPrice()
                                        .multiply(
                                                BigDecimal.valueOf(
                                                        i.getQuantity()
                                                )
                                        );

                        line =
                                line.add(
                                        modifierTotal
                                );
                    }
                }
            }

            sub =
                    sub.add(line);
        }

        // =========================================================
        // SUBTOTAL
        // =========================================================

        o.setSubtotal(sub);

        // =========================================================
        // DISCOUNT
        // =========================================================

        if (o.getDiscount() == null) {

            o.setDiscount(
                    BigDecimal.ZERO
            );
        }

        // =========================================================
        // TAXABLE AMOUNT
        // =========================================================

        BigDecimal taxable =
                sub.subtract(
                        o.getDiscount()
                ).max(
                        BigDecimal.ZERO
                );

        // =========================================================
        // 5% TAX
        // =========================================================

        o.setTax(
                taxable.multiply(
                        new BigDecimal("0.05")
                )
        );

        // =========================================================
        // FINAL TOTAL
        // =========================================================

        o.setTotal(
                taxable.add(
                        o.getTax()
                )
        );
    }

    // =========================================================
    // KITCHEN ORDERS
    // ONLY PREPARING AND READY
    // =========================================================

    public List<Order> kitchen() {

        return orders.findByStatusInOrderByCreatedAtAsc(
                List.of(
                        "PREPARING",
                        "READY"
                )
        );
    }

    // =========================================================
    // UPDATE ORDER STATUS
    // ONLY PREPARING / READY
    // =========================================================

    public Order status(
            String id,
            String s) {

        if (s == null || s.isBlank()) {

            throw new RuntimeException(
                    "Order status is required"
            );
        }

        String normalizedStatus =
                s.trim().toUpperCase();

        if (!Set.of(
                "PREPARING",
                "READY"
        ).contains(normalizedStatus)) {

            throw new RuntimeException(
                    "Invalid order status: "
                            + s
                            + ". Allowed: PREPARING, READY"
            );
        }

        Order o =
                orders.findById(id)
                        .orElseThrow();

        o.setStatus(
                normalizedStatus
        );

        o.setUpdatedAt(
                Instant.now()
        );

        return orders.save(o);
    }

    // =========================================================
    // GET ORDER BY ORDER ID
    // =========================================================

    public Order getOrderByOrderId(
            String orderId) {

        if (orderId == null ||
                orderId.isBlank()) {

            throw new IllegalArgumentException(
                    "Order ID is required"
            );
        }

        return orders.findByOrderId(
                orderId
        ).orElseThrow(() ->
                new RuntimeException(
                        "Order not found: "
                                + orderId
                )
        );
    }

    // =========================================================
    // GET PREPARING ORDERS
    // =========================================================

    public List<Order> getPreparingOrders() {

        return orders.findByStatusOrderByCreatedAtAsc(
                "PREPARING"
        );
    }

    // =========================================================
    // GET READY ORDERS
    // =========================================================

    public List<Order> getReadyOrders() {

        return orders.findByStatusOrderByCreatedAtAsc(
                "READY"
        );
    }
}


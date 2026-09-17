package com.restaurant.platform.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("orders")
public class Order {

    // MongoDB internal ID
    @Id
    private String id;

    // Business/display order ID
    private String orderId;

    private String kioskId;
    private String pickupNumber;
    private String orderType;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private String customerId;

    private List<Item> items = new ArrayList<>();

    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    private Instant createdAt;
    private Instant updatedAt;

    private Boolean priority = false;

    public Order() {
    }

    // ==========================================
    // MongoDB ID
    // ==========================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // ==========================================
    // BUSINESS ORDER ID
    // ==========================================

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // ==========================================
    // KIOSK
    // ==========================================

    public String getKioskId() {
        return kioskId;
    }

    public void setKioskId(String kioskId) {
        this.kioskId = kioskId;
    }

    // ==========================================
    // PICKUP NUMBER
    // ==========================================

    public String getPickupNumber() {
        return pickupNumber;
    }

    public void setPickupNumber(String pickupNumber) {
        this.pickupNumber = pickupNumber;
    }

    // ==========================================
    // ORDER TYPE
    // ==========================================

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    // ==========================================
    // STATUS
    // ==========================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ==========================================
    // PAYMENT METHOD
    // ==========================================

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // ==========================================
    // PAYMENT STATUS
    // ==========================================

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }



    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }



    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }



    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }



    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ==========================================
    // PRIORITY
    // ==========================================

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    // ==========================================
    // BUILDER
    // ==========================================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Order value = new Order();

        public Builder id(String value) {
            this.value.setId(value);
            return this;
        }

        public Builder orderId(String value) {
            this.value.setOrderId(value);
            return this;
        }

        public Builder kioskId(String value) {
            this.value.setKioskId(value);
            return this;
        }

        public Builder pickupNumber(String value) {
            this.value.setPickupNumber(value);
            return this;
        }

        public Builder orderType(String value) {
            this.value.setOrderType(value);
            return this;
        }

        public Builder status(String value) {
            this.value.setStatus(value);
            return this;
        }

        public Builder paymentMethod(String value) {
            this.value.setPaymentMethod(value);
            return this;
        }

        public Builder paymentStatus(String value) {
            this.value.setPaymentStatus(value);
            return this;
        }

        public Builder customerId(String value) {
            this.value.setCustomerId(value);
            return this;
        }

        public Builder items(List<Item> value) {
            this.value.setItems(value);
            return this;
        }

        public Builder subtotal(BigDecimal value) {
            this.value.setSubtotal(value);
            return this;
        }

        public Builder tax(BigDecimal value) {
            this.value.setTax(value);
            return this;
        }

        public Builder discount(BigDecimal value) {
            this.value.setDiscount(value);
            return this;
        }

        public Builder total(BigDecimal value) {
            this.value.setTotal(value);
            return this;
        }

        public Builder createdAt(Instant value) {
            this.value.setCreatedAt(value);
            return this;
        }

        public Builder updatedAt(Instant value) {
            this.value.setUpdatedAt(value);
            return this;
        }

        public Builder priority(Boolean value) {
            this.value.setPriority(value);
            return this;
        }

        public Order build() {
            return value;
        }
    }

    // ==========================================
    // ITEM
    // ==========================================

    public static class Item {

        private String productId;
        private String name;
        private String notes;

        private BigDecimal unitPrice;
        private Integer quantity;

        private List<SelectedModifier> modifiers = new ArrayList<>();

        public Item() {
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public List<SelectedModifier> getModifiers() {
            return modifiers;
        }

        public void setModifiers(List<SelectedModifier> modifiers) {
            this.modifiers = modifiers;
        }

        
  

    private Double price;

    private String categoryId;

    /**
     * Kitchen-specific status.
     *
     * NEW
     * ACCEPTED
     * PREPARING
     * READY
     */
    private String kitchenStatus = "NEW";

    // getters and setters

  

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getKitchenStatus() {
        return kitchenStatus;
    }

    public void setKitchenStatus(String kitchenStatus) {
        this.kitchenStatus = kitchenStatus;
    }
    }

    // ==========================================
    // SELECTED MODIFIER
    // ==========================================

    public static class SelectedModifier {

        private String modifierId;
        private String optionId;
        private String name;
        private BigDecimal price;

        public SelectedModifier() {
        }

        public String getModifierId() {
            return modifierId;
        }

        public void setModifierId(String modifierId) {
            this.modifierId = modifierId;
        }

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }


}

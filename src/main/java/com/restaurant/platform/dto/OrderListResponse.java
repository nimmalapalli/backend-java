package com.restaurant.platform.dto;

import com.restaurant.platform.model.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderListResponse {

    private String orderId;
    private String tokenNumber;

    private String kioskId;
    private String pickupNumber;
    private String orderType;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private String customerId;

    private List<Order.Item> items;

    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;

    private Instant createdAt;
    private Instant updatedAt;

    private Boolean priority;

    public OrderListResponse() {
    }

    public OrderListResponse(
            Order order,
            String tokenNumber) {

        this.orderId = order.getOrderId();
        this.tokenNumber = tokenNumber;

        this.kioskId = order.getKioskId();
        this.pickupNumber = order.getPickupNumber();
        this.orderType = order.getOrderType();
        this.status = order.getStatus();
        this.paymentMethod = order.getPaymentMethod();
        this.paymentStatus = order.getPaymentStatus();
        this.customerId = order.getCustomerId();

        this.items = order.getItems();

        this.subtotal = order.getSubtotal();
        this.tax = order.getTax();
        this.discount = order.getDiscount();
        this.total = order.getTotal();

        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();

        this.priority = order.getPriority();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getKioskId() {
        return kioskId;
    }

    public void setKioskId(String kioskId) {
        this.kioskId = kioskId;
    }

    public String getPickupNumber() {
        return pickupNumber;
    }

    public void setPickupNumber(String pickupNumber) {
        this.pickupNumber = pickupNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

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

    public List<Order.Item> getItems() {
        return items;
    }

    public void setItems(List<Order.Item> items) {
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

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }
}

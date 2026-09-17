package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payments")
public class Payment {
    @Id
    private String id = null;
    private String orderId = null;
    private String method = null;
    private java.math.BigDecimal amount = java.math.BigDecimal.ZERO;
    private String status = null;
    private String transactionId = null;
    private java.time.Instant createdAt = null;
    private String cashToken = null;
    public Payment() {}

    public Payment(String id, String orderId, String method, java.math.BigDecimal amount, String status, String transactionId, java.time.Instant createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.method = method;
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public java.math.BigDecimal getAmount() { return amount; }
    public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public java.time.Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.Instant createdAt) { this.createdAt = createdAt; }
public String getCashToken() {
    return cashToken;
}

public void setCashToken(String cashToken) {
    this.cashToken = cashToken;
}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Payment value = new Payment();
        public Builder id(String v) { value.setId(v); return this; }
        public Builder orderId(String v) { value.setOrderId(v); return this; }
        public Builder method(String v) { value.setMethod(v); return this; }
        public Builder amount(java.math.BigDecimal v) { value.setAmount(v); return this; }
        public Builder status(String v) { value.setStatus(v); return this; }
        public Builder transactionId(String v) { value.setTransactionId(v); return this; }
        public Builder createdAt(java.time.Instant v) { value.setCreatedAt(v); return this; }
        public Payment build() { return value; }
    }
}
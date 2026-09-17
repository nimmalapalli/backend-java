package com.restaurant.platform.dto;

import java.math.BigDecimal;

public class CashTokenRequest {

    private String orderId;
    private BigDecimal amount;

    public CashTokenRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

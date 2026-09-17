package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("restaurant_settings")
public class RestaurantSettings {
    @Id
    private String id = null;
    private String name = null;
    private String logo = null;
    private String currency = null;
    private Boolean taxEnabled = true;
    private java.math.BigDecimal taxRate = java.math.BigDecimal.ZERO;
    private Integer idleTimeout = 0;
    public RestaurantSettings() {}

    public RestaurantSettings(String id, String name, String logo, String currency, Boolean taxEnabled, java.math.BigDecimal taxRate, Integer idleTimeout) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.currency = currency;
        this.taxEnabled = taxEnabled;
        this.taxRate = taxRate;
        this.idleTimeout = idleTimeout;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Boolean getTaxEnabled() { return taxEnabled; }
    public void setTaxEnabled(Boolean taxEnabled) { this.taxEnabled = taxEnabled; }
    public java.math.BigDecimal getTaxRate() { return taxRate; }
    public void setTaxRate(java.math.BigDecimal taxRate) { this.taxRate = taxRate; }
    public Integer getIdleTimeout() { return idleTimeout; }
    public void setIdleTimeout(Integer idleTimeout) { this.idleTimeout = idleTimeout; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final RestaurantSettings value = new RestaurantSettings();
        public Builder id(String v) { value.setId(v); return this; }
        public Builder name(String v) { value.setName(v); return this; }
        public Builder logo(String v) { value.setLogo(v); return this; }
        public Builder currency(String v) { value.setCurrency(v); return this; }
        public Builder taxEnabled(Boolean v) { value.setTaxEnabled(v); return this; }
        public Builder taxRate(java.math.BigDecimal v) { value.setTaxRate(v); return this; }
        public Builder idleTimeout(Integer v) { value.setIdleTimeout(v); return this; }
        public RestaurantSettings build() { return value; }
    }
}
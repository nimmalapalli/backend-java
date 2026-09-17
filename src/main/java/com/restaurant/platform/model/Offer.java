package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("offers")
public class Offer {
    @Id
    private String id = null;
    private String name = null;
    private String type = null;
    private java.math.BigDecimal value = java.math.BigDecimal.ZERO;
    private java.math.BigDecimal minimumOrder = java.math.BigDecimal.ZERO;
    private Boolean active = true;
    public Offer() {}

    public Offer(String id, String name, String type, java.math.BigDecimal value, java.math.BigDecimal minimumOrder, Boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.minimumOrder = minimumOrder;
        this.active = active;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public java.math.BigDecimal getValue() { return value; }
    public void setValue(java.math.BigDecimal value) { this.value = value; }
    public java.math.BigDecimal getMinimumOrder() { return minimumOrder; }
    public void setMinimumOrder(java.math.BigDecimal minimumOrder) { this.minimumOrder = minimumOrder; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Offer value = new Offer();
        public Builder id(String v) { value.setId(v); return this; }
        public Builder name(String v) { value.setName(v); return this; }
        public Builder type(String v) { value.setType(v); return this; }
        public Builder value(java.math.BigDecimal v) { value.setValue(v); return this; }
        public Builder minimumOrder(java.math.BigDecimal v) { value.setMinimumOrder(v); return this; }
        public Builder active(Boolean v) { value.setActive(v); return this; }
        public Offer build() { return value; }
    }
}
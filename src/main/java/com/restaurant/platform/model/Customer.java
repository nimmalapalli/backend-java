package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customers")
public class Customer {
    @Id
    private String id = null;
    private String name = null;
    private String phone = null;
    private String email = null;
    public Customer() {}

    public Customer(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Customer value = new Customer();
        public Builder id(String v) { value.setId(v); return this; }
        public Builder name(String v) { value.setName(v); return this; }
        public Builder phone(String v) { value.setPhone(v); return this; }
        public Builder email(String v) { value.setEmail(v); return this; }
        public Customer build() { return value; }
    }
}
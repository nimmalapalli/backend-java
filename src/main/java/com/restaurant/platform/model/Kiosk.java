package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("kiosks")
public class Kiosk {
    @Id
    private String id = null;
    private String name = null;
    private String location = null;
    private String status = null;
    private java.time.Instant lastSeen = null;
    private Boolean active = true;
    public Kiosk() {}

    public Kiosk(String id, String name, String location, String status, java.time.Instant lastSeen, Boolean active) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.status = status;
        this.lastSeen = lastSeen;
        this.active = active;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public java.time.Instant getLastSeen() { return lastSeen; }
    public void setLastSeen(java.time.Instant lastSeen) { this.lastSeen = lastSeen; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Kiosk value = new Kiosk();
        public Builder id(String v) { value.setId(v); return this; }
        public Builder name(String v) { value.setName(v); return this; }
        public Builder location(String v) { value.setLocation(v); return this; }
        public Builder status(String v) { value.setStatus(v); return this; }
        public Builder lastSeen(java.time.Instant v) { value.setLastSeen(v); return this; }
        public Builder active(Boolean v) { value.setActive(v); return this; }
        public Kiosk build() { return value; }
    }
}
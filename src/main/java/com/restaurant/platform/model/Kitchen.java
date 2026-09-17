package com.restaurant.platform.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("kitchens")
public class Kitchen {

    @Id
    private String id = null;

    private String name = null;
    private String status = null;

    private List<String> stations = new ArrayList<>();

    
    

    // Categories handled by this kitchen
    private List<String> categoryIds = new ArrayList<>();

    private Integer autoRefreshSeconds = 0;
    private Boolean soundEnabled = true;

    // ==========================================
    // DEFAULT CONSTRUCTOR
    // ==========================================

    public Kitchen() {
    }

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Kitchen(
            String id,
            String name,
            String status,
            List<String> stations,
            List<String> categoryIds,
            Integer autoRefreshSeconds,
            Boolean soundEnabled) {

        this.id = id;
        this.name = name;
        this.status = status;
        this.stations = stations;
        this.categoryIds = categoryIds;
        this.autoRefreshSeconds = autoRefreshSeconds;
        this.soundEnabled = soundEnabled;
    }

    // ==========================================
    // ID
    // ==========================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // ==========================================
    // NAME
    // ==========================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    // STATIONS
    // ==========================================

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    // ==========================================
    // CATEGORY IDS
    // ==========================================

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    // ==========================================
    // AUTO REFRESH
    // ==========================================

    public Integer getAutoRefreshSeconds() {
        return autoRefreshSeconds;
    }

    public void setAutoRefreshSeconds(Integer autoRefreshSeconds) {
        this.autoRefreshSeconds = autoRefreshSeconds;
    }

    // ==========================================
    // SOUND
    // ==========================================

    public Boolean getSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(Boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    // ==========================================
    // BUILDER
    // ==========================================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Kitchen value = new Kitchen();

        public Builder id(String v) {
            value.setId(v);
            return this;
        }

        public Builder name(String v) {
            value.setName(v);
            return this;
        }

        public Builder status(String v) {
            value.setStatus(v);
            return this;
        }

        public Builder stations(List<String> v) {
            value.setStations(v);
            return this;
        }

        public Builder categoryIds(List<String> v) {
            value.setCategoryIds(v);
            return this;
        }

        public Builder autoRefreshSeconds(Integer v) {
            value.setAutoRefreshSeconds(v);
            return this;
        }

        public Builder soundEnabled(Boolean v) {
            value.setSoundEnabled(v);
            return this;
        }

        public Kitchen build() {
            return value;
        }
    }
}

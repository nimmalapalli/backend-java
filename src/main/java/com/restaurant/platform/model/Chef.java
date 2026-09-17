package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("chefs")
public class Chef {

    @Id
    private String id;

    private String chefName;

    private String kitchenId;

    public Chef() {
    }

    public Chef(String id, String chefName, String kitchenId) {
        this.id = id;
        this.chefName = chefName;
        this.kitchenId = kitchenId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(String kitchenId) {
        this.kitchenId = kitchenId;
    }
}
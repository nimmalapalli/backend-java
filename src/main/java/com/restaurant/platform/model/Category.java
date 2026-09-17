package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document("categories")
public class Category {

    @Id
    private String id;

    private String name;

    private Map<String, String> nameTranslations = new HashMap<>();

    private String image;

    private Integer sortOrder = 0;

    private Boolean active = true;

    public Category() {
    }

    public Category(
            String id,
            String name,
            String image,
            Integer sortOrder,
            Boolean active
    ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.sortOrder = sortOrder;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(
            Map<String, String> nameTranslations
    ) {
        this.nameTranslations =
                nameTranslations != null
                        ? nameTranslations
                        : new HashMap<>();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Category value = new Category();

        public Builder id(String v) {
            value.setId(v);
            return this;
        }

        public Builder name(String v) {
            value.setName(v);
            return this;
        }

        public Builder nameTranslations(
                Map<String, String> v
        ) {
            value.setNameTranslations(v);
            return this;
        }

        public Builder image(String v) {
            value.setImage(v);
            return this;
        }

        public Builder sortOrder(Integer v) {
            value.setSortOrder(v);
            return this;
        }

        public Builder active(Boolean v) {
            value.setActive(v);
            return this;
        }

        public Category build() {
            return value;
        }
    }
}
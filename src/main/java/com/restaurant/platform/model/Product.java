package com.restaurant.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document("products")
public class Product {

    @Id
    private String id;

    private String categoryId;

    private List<String> categoryIds = new ArrayList<>();

    private String name;

    private Map<String, String> nameTranslations =
            new HashMap<>();

    private String description;

    private Map<String, String> descriptionTranslations =
            new HashMap<>();

    private String image;

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal taxRate = BigDecimal.ZERO;

    private Boolean available = true;

    private Boolean featured = false;

    private Integer sortOrder = 0;

    private List<String> modifierIds = new ArrayList<>();

    public Product() {
    }

    public Product(
            String id,
            String categoryId,
            String name,
            String description,
            String image,
            BigDecimal price,
            BigDecimal taxRate,
            Boolean available,
            Boolean featured,
            Integer sortOrder,
            List<String> modifierIds
    ) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.taxRate = taxRate;
        this.available = available;
        this.featured = featured;
        this.sortOrder = sortOrder;
        this.modifierIds = modifierIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds =
                categoryIds != null
                        ? categoryIds
                        : new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getDescriptionTranslations() {
        return descriptionTranslations;
    }

    public void setDescriptionTranslations(
            Map<String, String> descriptionTranslations
    ) {
        this.descriptionTranslations =
                descriptionTranslations != null
                        ? descriptionTranslations
                        : new HashMap<>();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<String> getModifierIds() {
        return modifierIds;
    }

    public void setModifierIds(List<String> modifierIds) {
        this.modifierIds =
                modifierIds != null
                        ? modifierIds
                        : new ArrayList<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Product value = new Product();

        public Builder id(String v) {
            value.setId(v);
            return this;
        }

        public Builder categoryId(String v) {
            value.setCategoryId(v);
            return this;
        }

        public Builder categoryIds(List<String> v) {
            value.setCategoryIds(v);
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

        public Builder description(String v) {
            value.setDescription(v);
            return this;
        }

        public Builder descriptionTranslations(
                Map<String, String> v
        ) {
            value.setDescriptionTranslations(v);
            return this;
        }

        public Builder image(String v) {
            value.setImage(v);
            return this;
        }

        public Builder price(BigDecimal v) {
            value.setPrice(v);
            return this;
        }

        public Builder taxRate(BigDecimal v) {
            value.setTaxRate(v);
            return this;
        }

        public Builder available(Boolean v) {
            value.setAvailable(v);
            return this;
        }

        public Builder featured(Boolean v) {
            value.setFeatured(v);
            return this;
        }

        public Builder sortOrder(Integer v) {
            value.setSortOrder(v);
            return this;
        }

        public Builder modifierIds(List<String> v) {
            value.setModifierIds(v);
            return this;
        }

        public Product build() {
            return value;
        }
    }
}
package com.restaurant.platform.dto;

public record LocalizedCategoryResponse(
        String id,
        String name,
        String image,
        Integer sortOrder,
        Boolean active
) {
}
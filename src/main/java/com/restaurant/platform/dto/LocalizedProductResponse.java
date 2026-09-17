package com.restaurant.platform.dto;

import java.math.BigDecimal;
import java.util.List;

public record LocalizedProductResponse(
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
}
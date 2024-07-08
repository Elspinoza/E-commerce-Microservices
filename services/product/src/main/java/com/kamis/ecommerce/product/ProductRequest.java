package com.kamis.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,

        @Positive(message = "Available quantity should be positive")
        double available_quantity,

        @Positive(message = "Product price is required")
        BigDecimal price,

        @NotNull(message = "Product price is required")
        Integer categoryId
) {
}

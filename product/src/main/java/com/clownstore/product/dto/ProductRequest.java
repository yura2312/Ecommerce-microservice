package com.clownstore.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record ProductRequest(
        @NotBlank String name,
        @NotNull BigDecimal price,
        @NotNull int stock,
        @NotBlank String description,
        @NotNull Map<String, String> attributes
) {
}

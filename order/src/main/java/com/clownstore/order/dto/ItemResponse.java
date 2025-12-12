package com.clownstore.order.dto;

import java.math.BigDecimal;

public record ItemResponse(
        String id,
        String name,
        BigDecimal price,
        int quantity
) {
}

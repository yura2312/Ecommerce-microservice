package com.clownstore.cart.dto;

import java.math.BigDecimal;

public record ProductResponse(
        String userId,
        BigDecimal price,
        int stock
) {
}

package com.clownstore.order.dto;

import java.math.BigDecimal;
import java.util.Map;

public record CartResponse(
        String userId,
        Map<String, ItemResponse> items,
        BigDecimal total
) {
}

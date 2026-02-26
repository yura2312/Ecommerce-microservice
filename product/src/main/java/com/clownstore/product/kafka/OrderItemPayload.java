package com.clownstore.product.kafka;

import lombok.Builder;

@Builder
public record OrderItemPayload(
        String productId,
        int quantity
) {
}

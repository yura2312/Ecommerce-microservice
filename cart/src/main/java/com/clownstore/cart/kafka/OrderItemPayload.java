package com.clownstore.cart.kafka;

import lombok.Builder;

@Builder
public record OrderItemPayload(
        String productId,
        int quantity
) {
}

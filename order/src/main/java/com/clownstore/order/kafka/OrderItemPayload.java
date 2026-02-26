package com.clownstore.order.kafka;

import lombok.Builder;

@Builder
public record OrderItemPayload(
        String productId,
        int quantity
) {
}

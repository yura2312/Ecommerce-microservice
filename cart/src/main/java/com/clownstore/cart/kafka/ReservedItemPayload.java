package com.clownstore.cart.kafka;

import lombok.Builder;

@Builder
public record ReservedItemPayload(
        String productId,
        int quantity
) {
}


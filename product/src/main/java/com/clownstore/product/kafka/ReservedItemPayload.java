package com.clownstore.product.kafka;

import lombok.Builder;

@Builder
public record ReservedItemPayload(
        String productId,
        int quantity
) {
}


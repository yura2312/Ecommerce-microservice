package com.clownstore.order.kafka;

import lombok.Builder;

@Builder
public record ReservedItemPayload(
        String productId,
        int quantity
) {
}


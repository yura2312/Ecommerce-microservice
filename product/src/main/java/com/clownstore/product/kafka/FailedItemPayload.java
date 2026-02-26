package com.clownstore.product.kafka;

import lombok.Builder;

@Builder
public record FailedItemPayload(
        String productId,
        int requestedQuantity,
        int availableStock
) {
}


package com.clownstore.product.kafka;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderCreatedEvent(
        Long id,
        String userId,
        List<OrderItemPayload> items
) {
}

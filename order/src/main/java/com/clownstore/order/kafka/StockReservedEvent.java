package com.clownstore.order.kafka;

import lombok.Builder;

import java.util.List;

@Builder
public record StockReservedEvent(
        Long orderId,
        String userId,
        List<ReservedItemPayload> items
) {
}


package com.clownstore.order.kafka;

import lombok.Builder;

import java.util.List;

@Builder
public record StockReservationFailedEvent(
        Long orderId,
        String userId,
        List<FailedItemPayload> insufficientItems
) {
}


package com.clownstore.order.mapper;

import com.clownstore.order.dto.ItemResponse;
import com.clownstore.order.kafka.OrderItemPayload;
import com.clownstore.order.model.OrderItem;

import java.math.BigDecimal;

public class OrderItemMapper {

    public static OrderItem toOrderItem(ItemResponse item) {
        return OrderItem.builder()
                .productId(item.id())
                .productName(item.name())
                .price(item.price())
                .quantity(item.quantity())
                .subtotal(item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .build();
    }

    public static OrderItemPayload craeteOrderItemPayloadFrom(OrderItem orderItem) {

        return OrderItemPayload.builder()
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .build();
    }

}

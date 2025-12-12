package com.clownstore.order.mapper;

import com.clownstore.order.dto.CartResponse;
import com.clownstore.order.model.Order;
import com.clownstore.order.model.Status;

import java.time.LocalDateTime;

public class OrderMapper {
    public static Order toOrder(CartResponse cart) {
        Order order = Order.builder()
                .userId(cart.userId())
                .status(Status.PENDING)
                .totalAmount(cart.total())
                .createdAt(LocalDateTime.now())
                .items(
                        cart.items().values().stream()
                                .map(OrderItemMapper::toOrderItem)
                                .toList()
                )
                .build();

        order.getItems().forEach(item -> item.setOrder(order));
        return order;
    }
}

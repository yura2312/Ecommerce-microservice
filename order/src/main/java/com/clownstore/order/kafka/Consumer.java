package com.clownstore.order.kafka;

import com.clownstore.order.model.Order;
import com.clownstore.order.model.Status;
import com.clownstore.order.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final OrderRepository orderRepository;
    private static final String STOCK_RESERVED = "stock-reserved";

    public Consumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = STOCK_RESERVED, groupId = "order-group")
    public void updateOrderStatus(StockReservedEvent event) {
        Order order = orderRepository.findById(event.orderId()).get();
        order.setStatus(Status.PAID);
        orderRepository.save(order);
    }
}

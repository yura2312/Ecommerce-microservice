package com.clownstore.order.service;

import com.clownstore.order.dto.CartResponse;
import com.clownstore.order.kafka.OrderCreatedEvent;
import com.clownstore.order.mapper.OrderMapper;
import com.clownstore.order.model.Order;
import com.clownstore.order.repository.CartClient;
import com.clownstore.order.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final String ORDER_CREATED = "order-created";

    private OrderRepository repository;
    private CartClient cartClient;
    private KafkaTemplate<String, OrderCreatedEvent> template;

    public OrderService(OrderRepository repository, CartClient cartClient, KafkaTemplate<String, OrderCreatedEvent> template) {
        this.repository = repository;
        this.cartClient = cartClient;
        this.template = template;
    }

    public Order save(String userId) {
        CartResponse cart = cartClient.getCart(userId);
        if (cart == null) {
            throw new RuntimeException("cart not found"); //TODO: Exception for cart
        }
        Order order = OrderMapper.toOrder(cart);
        return repository.save(order);
    }

    public void delete(String userId) {
        repository.deleteByUserId(userId);
    }

    public Order createOrder(String userId) {

        CartResponse cart = cartClient.getCart(userId);
        Order order = OrderMapper.toOrder(cart);
        Order savedOrder = repository.save(order);
        OrderCreatedEvent event = OrderMapper.createOrderEventFrom(savedOrder);
        template.send(ORDER_CREATED, event.userId(), event);
        return savedOrder;
    }

    public Order getOrder(String userId) {
        return repository.findOrderByUserId(userId);
    }
}

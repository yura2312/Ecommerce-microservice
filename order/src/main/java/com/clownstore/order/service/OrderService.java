package com.clownstore.order.service;

import com.clownstore.order.dto.CartResponse;
import com.clownstore.order.mapper.OrderMapper;
import com.clownstore.order.model.Order;
import com.clownstore.order.repository.CartClient;
import com.clownstore.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    OrderRepository repository;
    CartClient cartClient;
    public OrderService(OrderRepository repository, CartClient cartClient) {
        this.repository = repository;
        this.cartClient = cartClient;
    }

    public Order save(String userId){
        CartResponse cart = cartClient.getCart(userId);
        if (cart == null) {
            throw new RuntimeException("cart not found"); //TODO: Exception for cart
        }
        Order order = OrderMapper.toOrder(cart);
        return repository.save(order);
    }

    public void delete(String userId){
        repository.deleteByUserId(userId);
    }

}

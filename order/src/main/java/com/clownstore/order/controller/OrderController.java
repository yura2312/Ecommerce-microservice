package com.clownstore.order.controller;

import com.clownstore.order.model.Order;
import com.clownstore.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Order> save(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        Order order = service.save(userId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        service.delete(userId);
        return ResponseEntity.noContent().build();
    }
}

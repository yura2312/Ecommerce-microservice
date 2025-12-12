package com.clownstore.order.repository;

import com.clownstore.order.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product", url = "http://localhost:8082/cart")
public interface CartClient {

    @GetMapping("/")
    public CartResponse getCart(@RequestParam String userId);
}

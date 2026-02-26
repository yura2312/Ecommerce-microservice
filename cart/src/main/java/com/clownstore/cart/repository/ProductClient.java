package com.clownstore.cart.repository;

import com.clownstore.cart.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product", url = "${product.service.url}/product")
public interface ProductClient {

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable String id);
}

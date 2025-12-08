package com.clownstore.cart.service;

import com.clownstore.cart.dto.ProductResponse;
import com.clownstore.cart.exception.CartNotFoundException;
import com.clownstore.cart.exception.ProductNotFound;
import com.clownstore.cart.model.Cart;
import com.clownstore.cart.repository.CartRepository;
import com.clownstore.cart.repository.ProductClient;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    CartRepository repository;
    ProductClient productClient;
    public CartService(CartRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    public Cart find(String id){
        return repository.findById(id)
                .orElseThrow( () -> new CartNotFoundException("Cart not found"));
    }

    public Cart addToCart(String productId, int quantity){
        ProductResponse product = productClient.getProduct(productId);
        if (product == null){
            throw new ProductNotFound("Product not found");
        }
        repository.save()
    }
}

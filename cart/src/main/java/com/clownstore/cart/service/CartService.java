package com.clownstore.cart.service;

import com.clownstore.cart.dto.ProductResponse;
import com.clownstore.cart.exception.CartNotFoundException;
import com.clownstore.cart.exception.NotEnoughStockException;
import com.clownstore.cart.exception.ProductNotFoundException;
import com.clownstore.cart.mapper.CartItemMapper;
import com.clownstore.cart.model.Cart;
import com.clownstore.cart.repository.CartRepository;
import com.clownstore.cart.repository.ProductClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class CartService {

    private final RedisTemplate<Object, Object> redisTemplate;
    CartRepository repository;
    ProductClient productClient;
    RedisTemplate template;


    public CartService(CartRepository repository, ProductClient productClient, RedisTemplate<Object, Object> redisTemplate) {
        this.repository = repository;
        this.productClient = productClient;
        this.redisTemplate = redisTemplate;
        this.template = redisTemplate;
    }

    public Cart findById(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    public Cart addToCart(String userid, String productId, int quantity) {
        ProductResponse product = productClient.getProduct(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }

        if (product.stock() < quantity) {
            throw new NotEnoughStockException("Quantity is higher than stock availability");
        }

        Cart cart = repository.findById(userid)
                .orElse(
                        Cart.builder()
                                .userId(userid).items(new HashMap<>()).total(BigDecimal.ZERO).build()
                );

        cart.getItems()
                .compute(productId, (key, item) -> {
                    if (item == null){
                        return CartItemMapper.toCartItem(product, quantity);
                    }
                    item.addQuantity(quantity);
                    return item;
                });

        BigDecimal total = cart.getItems().values().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotal(total);

        return repository.save(cart);
    }

    public List<Cart> findAll(){
        return (List<Cart>) repository.findAll();
    }
}

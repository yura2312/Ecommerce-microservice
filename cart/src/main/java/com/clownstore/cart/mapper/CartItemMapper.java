package com.clownstore.cart.mapper;

import com.clownstore.cart.dto.ProductResponse;
import com.clownstore.cart.model.CartItem;

public class CartItemMapper {

    public static CartItem toCartItem(ProductResponse productResponse, int quantity){
        return CartItem.builder()
                .id(productResponse.id())
                .name(productResponse.name())
                .price(productResponse.price())
                .quantity(quantity)
                .build();
    }
}

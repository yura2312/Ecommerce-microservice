package com.clownstore.cart.model;

import com.clownstore.cart.dto.ProductResponse;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RedisHash
@Builder
@NoArgsConstructor
public class Cart {

    @Id
    private String userId;

    //Product ID: Product
    private Map<String, CartItem> items;

    private BigDecimal total;
}

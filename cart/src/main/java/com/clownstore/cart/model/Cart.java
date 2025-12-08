package com.clownstore.cart.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RedisHash
public class Cart {

    @Id
    private String userId;

    @NotNull
    private Map<String, Integer> items;

    private BigDecimal total;
}

package com.clownstore.cart.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    private int quantity;

    public void addQuantity(int quantity){
        this.quantity += quantity;
    }
}

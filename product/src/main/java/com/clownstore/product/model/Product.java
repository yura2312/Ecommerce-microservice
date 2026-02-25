package com.clownstore.product.model;

import com.mongodb.annotations.Beta;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Document("Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int stock;

    @NotNull
    private String description;

    @NotNull
    private Map<String, String> attributes;
}

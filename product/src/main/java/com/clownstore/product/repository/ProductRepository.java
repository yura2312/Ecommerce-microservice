package com.clownstore.product.repository;

import com.clownstore.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByNameContaining(String name);

    String id(String id);
}

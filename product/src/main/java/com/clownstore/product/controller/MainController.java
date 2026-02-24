package com.clownstore.product.controller;

import com.clownstore.product.dto.ProductRequest;
import com.clownstore.product.model.Product;
import com.clownstore.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class MainController {

    ProductService service;

    public MainController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Product> findByName(@RequestParam String name) {
        var product = service.findByName(name);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/")
    public ResponseEntity<Product> save(@RequestBody @Valid ProductRequest productRequest) {
        var product = service.save(productRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAll() {
        var productList = service.findAll();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/hi")
    public String sayHi() {
        return "Hi there!";
    }
}

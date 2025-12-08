package com.clownstore.product.service;

import com.clownstore.product.exception.ProductExistsException;
import com.clownstore.product.exception.ProductNotFoundException;
import com.clownstore.product.model.Product;
import com.clownstore.product.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product){
        Optional<Product> productExists = repository.findByNameContaining(product.getName());
        if(productExists.isPresent()){
            throw new ProductExistsException("Product already exists");
        }
            return repository.save(product);
    }

    public Product  findByName(String name){
        return repository.findByNameContaining(name)
                .orElseThrow(() -> new ProductNotFoundException("Product with name " + name + " not found"));
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }

    public Product updatePriceByName(BigDecimal price,String name){
        Optional<Product> product = repository.findByNameContaining(name);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product with name " + name + " not found");
        }
        product.get().setPrice(price);
        return repository.save(product.get());
    }

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product findById(String id) {
        return repository.findById(id)
                .orElseThrow( () -> new ProductNotFoundException("Product with id " + id + " not found"));
    }
}

package com.clownstore.cart.controller;

import com.clownstore.cart.exception.CartNotFoundException;
import com.clownstore.cart.exception.NotEnoughStockException;
import com.clownstore.cart.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> exception(ProductNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage() + ". Check product's name/id");
        problem.setTitle("Product not found in DB");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ProblemDetail> exception(NotEnoughStockException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Not enough items in stock.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ProblemDetail> exception(CartNotFoundException ex){
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Cart doesn't belong to a registered user.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}

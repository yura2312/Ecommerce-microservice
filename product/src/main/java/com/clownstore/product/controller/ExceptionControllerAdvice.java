package com.clownstore.product.controller;

import com.clownstore.product.exception.ProductExistsException;
import com.clownstore.product.exception.ProductNotFoundException;
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
}

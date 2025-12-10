package com.clownstore.cart.exception;

public class NotEnoughStock extends RuntimeException {
    public NotEnoughStock(String message) {
        super(message);
    }
}

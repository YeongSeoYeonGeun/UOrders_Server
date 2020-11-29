package com.example.uorders.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long cartId) {
        super("Invalid cartId Exception : " + cartId);
        System.out.println(getMessage());
    }
}

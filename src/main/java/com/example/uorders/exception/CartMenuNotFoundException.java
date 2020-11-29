package com.example.uorders.exception;

public class CartMenuNotFoundException extends RuntimeException{
    public CartMenuNotFoundException(Long cartMenuId) {
        super("Invalid cartMenuId Exception : " + cartMenuId);
        System.out.println(getMessage());
    }
}

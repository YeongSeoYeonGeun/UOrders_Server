package com.example.uorders.exception;

public class OrderMenuNotFoundException extends RuntimeException {
    public OrderMenuNotFoundException(Long orderMenuId) {
        super("Invalid orderMenuId Exception : " + orderMenuId);
        System.out.println(getMessage());
    }
}

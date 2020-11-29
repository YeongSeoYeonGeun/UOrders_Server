package com.example.uorders.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long orderId) {
        super("Invalid orderId Exception : " + orderId);
        System.out.println(getMessage());
    }
}

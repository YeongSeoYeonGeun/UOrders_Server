package com.example.uorders.exception;

public class CafeNotFoundException extends RuntimeException {
    public CafeNotFoundException(Long cafeId) {
        super("Invalid cafeId Exception: " + cafeId);
        System.out.println(getMessage());
    }
}

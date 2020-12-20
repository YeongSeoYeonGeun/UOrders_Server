package com.example.uorders.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(Long menuId) {
        super("Invalid menuId Exception : " + menuId);
        System.out.println(getMessage());
    }
}

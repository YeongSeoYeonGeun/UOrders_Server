package com.example.uorders.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Invalid userId Exception: " + userId);
        System.out.println(getMessage());
    }
}

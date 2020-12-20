package com.example.uorders.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super("Not Found Exception " + message);
        System.out.println(getMessage());
    }
}

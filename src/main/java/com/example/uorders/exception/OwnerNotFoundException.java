package com.example.uorders.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(Long ownerId) {
        super("Invalid ownerId Exception : " + ownerId);
        System.out.println(getMessage());
    }
}

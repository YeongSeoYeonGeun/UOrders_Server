package com.example.uorders.exception;

public class FavoriteNotFoundException extends RuntimeException {
    public FavoriteNotFoundException(Long userId, Long cafeId) {
        super("Invalid userId or cafeId Exception: userId: " + userId + "   cafeId: "+ cafeId);
        System.out.println(getMessage());
    }
}

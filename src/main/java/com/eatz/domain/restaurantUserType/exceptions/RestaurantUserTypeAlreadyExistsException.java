package com.eatz.domain.restaurantUserType.exceptions;

public class RestaurantUserTypeAlreadyExistsException extends RuntimeException {
    public RestaurantUserTypeAlreadyExistsException(String message) {
        super(message);
    }
} 
package com.eatz.domain.restaurantUserType.exceptions;

public class RestaurantUserTypeNotFoundException extends RuntimeException {
    public RestaurantUserTypeNotFoundException(String message) {
        super(message);
    }

    public RestaurantUserTypeNotFoundException(Long id) {
        super("Restaurant user type not found with id: " + id);
    }
} 
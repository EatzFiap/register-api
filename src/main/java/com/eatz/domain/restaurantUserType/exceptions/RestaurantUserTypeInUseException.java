package com.eatz.domain.restaurantUserType.exceptions;

public class RestaurantUserTypeInUseException extends RuntimeException {
    public RestaurantUserTypeInUseException(String message) {
        super(message);
    }

    public RestaurantUserTypeInUseException(Long id) {
        super("Restaurant user type cannot be deleted. There are users associated with type ID: " + id);
    }
} 
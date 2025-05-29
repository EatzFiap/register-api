package com.eatz.domain.restaurantUser.exceptions;

public class RestaurantUserAlreadyExistsException extends RuntimeException {
    public RestaurantUserAlreadyExistsException(String message) {
        super(message);
    }
}

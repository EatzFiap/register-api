package com.eatz.application.restaurant.usecases;


import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import com.eatz.domain.restaurant.exceptions.RestaurantNotFoundException;

import java.util.List;
import java.util.UUID;

public class GetRestaurantUseCase {

    private final RestaurantRepository RestaurantRepository;

    public GetRestaurantUseCase(RestaurantRepository RestaurantRepository) {
        this.RestaurantRepository = RestaurantRepository;
    }

    public Restaurant execute(UUID id) throws RestaurantNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        return RestaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("User not found with id: " + id));
    }

    public List<Restaurant> execute() throws RestaurantNotFoundException {
        List<Restaurant> Restaurants = RestaurantRepository.findAll();
        if (Restaurants.isEmpty()) {
            throw new RestaurantNotFoundException("No users found");
        }
        return Restaurants;
    }
}

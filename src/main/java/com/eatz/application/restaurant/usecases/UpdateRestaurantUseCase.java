package com.eatz.application.restaurant.usecases;


import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;

import java.util.UUID;

public class UpdateRestaurantUseCase {

    private final RestaurantRepository RestaurantRepository;

    public UpdateRestaurantUseCase(RestaurantRepository RestaurantRepository) {
        this.RestaurantRepository = RestaurantRepository;
    }

    public Restaurant execute(UUID id, Restaurant newData) {
        if (id == null || newData == null) {
            throw new IllegalArgumentException("Id and user data must not be null");
        }

        Restaurant Restaurant = RestaurantRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Restaurant.setName(newData.getName());
        Restaurant.setEmail(newData.getEmail());
        Restaurant.setPassword(newData.getPassword());
        Restaurant.setAddress(newData.getAddress());

        return RestaurantRepository.save(Restaurant);
    }
}

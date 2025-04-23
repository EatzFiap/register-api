package com.eatz.application.restaurant.usecases;


import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;

import java.util.UUID;

public class DeleteRestaurantUseCase {
    private final RestaurantRepository RestaurantRepository;

    public DeleteRestaurantUseCase(RestaurantRepository RestaurantRepository) {
        this.RestaurantRepository = RestaurantRepository;
    }

    public void execute(UUID id) {
        Restaurant Restaurant = RestaurantRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Restaurant.setActive(false);
        RestaurantRepository.save(Restaurant);
    }
}

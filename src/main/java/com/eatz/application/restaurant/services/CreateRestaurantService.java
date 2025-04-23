package com.eatz.application.restaurant.services;

import com.eatz.application.restaurant.usecases.CreateRestaurantUseCase;

import com.eatz.domain.restaurant.Restaurant;
import org.springframework.stereotype.Service;

@Service
public class CreateRestaurantService {

    private final CreateRestaurantUseCase useCase;

    public CreateRestaurantService(CreateRestaurantUseCase useCase) {
        this.useCase = useCase;
    }

    public Restaurant execute(Restaurant restaurant) {
        return useCase.execute(restaurant);
    }
}
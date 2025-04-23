package com.eatz.application.restaurant.services;

import com.eatz.application.restaurant.usecases.UpdateRestaurantUseCase;

import com.eatz.domain.restaurant.Restaurant;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateRestaurantService {

    private final UpdateRestaurantUseCase useCase;

    public UpdateRestaurantService(UpdateRestaurantUseCase useCase) {
        this.useCase = useCase;
    }

    public Restaurant execute(UUID id, Restaurant newData) {
        return useCase.execute(id, newData);
    }
}

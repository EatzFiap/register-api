package com.eatz.application.restaurant.services;

import com.eatz.application.restaurant.usecases.DeleteRestaurantUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteRestaurantService {
    public final DeleteRestaurantUseCase deleteRestaurantUseCase;

    public DeleteRestaurantService(DeleteRestaurantUseCase useCase) {
        this.deleteRestaurantUseCase = useCase;
    }

    public void execute(UUID id) {
        deleteRestaurantUseCase.execute(id);
    }
}

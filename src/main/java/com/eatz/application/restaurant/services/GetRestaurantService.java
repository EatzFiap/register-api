package com.eatz.application.restaurant.services;

import com.eatz.application.restaurant.usecases.GetRestaurantUseCase;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.exceptions.RestaurantNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetRestaurantService {

    private final GetRestaurantUseCase useCase;

    public GetRestaurantService(GetRestaurantUseCase useCase) {
        this.useCase = useCase;
    }

    public Restaurant execute(UUID id) throws RestaurantNotFoundException {
        return useCase.execute(id);
    }

    public List<Restaurant> execute() throws RestaurantNotFoundException {
        return useCase.execute();
    }
}
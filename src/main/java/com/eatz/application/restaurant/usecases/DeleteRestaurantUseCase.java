package com.eatz.application.restaurant.usecases;

import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;


@Service
public class DeleteRestaurantUseCase {

    private final RestaurantRepository repository;

    public DeleteRestaurantUseCase(RestaurantRepository repository) {
        this.repository = repository;
    }

    public void execute(Restaurant restaurant) {
        repository.delete(restaurant);
    }
}

package com.eatz.application.restaurant.usecases;

import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetRestaurantUseCase {

    private final RestaurantRepository repository;

    public GetRestaurantUseCase(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }


        return repository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));
    }


    public List<Restaurant> findAll() {
        return repository.findAll();
    }
}
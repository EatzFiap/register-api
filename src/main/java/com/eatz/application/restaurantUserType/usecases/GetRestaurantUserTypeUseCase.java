package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetRestaurantUserTypeUseCase {
    private final RestaurantUserTypeRepository repository;

    public GetRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository) {
        this.repository = repository;
    }

    public RestaurantUserType executeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RestaurantUserTypeNotFoundException(id));
    }

    public List<RestaurantUserType> executeFindAll() {
        return repository.findAll();
    }
} 
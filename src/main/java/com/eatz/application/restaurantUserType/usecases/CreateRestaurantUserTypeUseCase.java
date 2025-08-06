package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateRestaurantUserTypeUseCase {
    private final RestaurantUserTypeRepository repository;

    public CreateRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository) {
        this.repository = repository;
    }

    public RestaurantUserType execute(RestaurantUserType restaurantUserType) {
        if (restaurantUserType == null) {
            throw new IllegalArgumentException("Restaurant user type cannot be null");
        }

        if (restaurantUserType.getName() == null || restaurantUserType.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant user type name cannot be null or empty");
        }

        if (repository.existsByName(restaurantUserType.getName())) {
            throw new RestaurantUserTypeAlreadyExistsException("Restaurant user type already exists with name: " + restaurantUserType.getName());
        }

        LocalDateTime now = LocalDateTime.now();
        restaurantUserType.setCreatedAt(now);
        restaurantUserType.setUpdatedAt(now);
        restaurantUserType.setDeleted(false);

        return repository.save(restaurantUserType);
    }
} 
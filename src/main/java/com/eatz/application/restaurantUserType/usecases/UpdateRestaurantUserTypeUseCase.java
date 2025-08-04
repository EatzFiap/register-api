package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateRestaurantUserTypeUseCase {
    private final RestaurantUserTypeRepository repository;

    public UpdateRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository) {
        this.repository = repository;
    }

    public RestaurantUserType execute(Long id, RestaurantUserType newData) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (newData == null) {
            throw new IllegalArgumentException("New data cannot be null");
        }

        RestaurantUserType existingType = repository.findById(id)
                .orElseThrow(() -> new RestaurantUserTypeNotFoundException(id));

        // Check if name is being changed and if it already exists
        if (newData.getName() != null && !newData.getName().equals(existingType.getName())) {
            if (repository.existsByName(newData.getName())) {
                throw new RestaurantUserTypeAlreadyExistsException(newData.getName());
            }
            existingType.setName(newData.getName());
        }

        if (newData.getDescription() != null) {
            existingType.setDescription(newData.getDescription());
        }

        existingType.setUpdatedAt(LocalDateTime.now());

        return repository.save(existingType);
    }
} 
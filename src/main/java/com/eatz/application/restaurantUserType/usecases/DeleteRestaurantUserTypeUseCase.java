package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeInUseException;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeleteRestaurantUserTypeUseCase {
    private final RestaurantUserTypeRepository repository;
    private final RestaurantUserRepository restaurantUserRepository;

    public DeleteRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository,
                                         RestaurantUserRepository restaurantUserRepository) {
        this.repository = repository;
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        RestaurantUserType restaurantUserType = repository.findById(id)
                .orElseThrow(() -> new RestaurantUserTypeNotFoundException(id));

        // Verificar se há usuários usando este tipo
        if (restaurantUserRepository.existsByRestaurantUserTypeIdAndIsDeletedFalse(id)) {
            throw new RestaurantUserTypeInUseException(id);
        }

        restaurantUserType.setDeleted(true);
        restaurantUserType.setUpdatedAt(LocalDateTime.now());

        repository.save(restaurantUserType);
    }
} 
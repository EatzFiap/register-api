package com.eatz.application.restaurant.usecases;

import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final SaveAddressUseCase saveAddressUseCase;

    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository, SaveAddressUseCase saveAddressUseCase) {
        this.restaurantRepository = restaurantRepository;
        this.saveAddressUseCase = saveAddressUseCase;
    }

    @Transactional
    public Restaurant execute(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }

        if (restaurant.getName() == null || restaurant.getName().isBlank()) {
            throw new IllegalArgumentException("Restaurant name is required");
        }

        if (restaurantRepository.existsByNameAndIsDeletedFalse(restaurant.getName())) {
            throw new IllegalArgumentException("A restaurant with this name already exists");
        }

        if (restaurant.getAddress() != null) {
            Address savedAddress = saveAddressUseCase.execute(restaurant.getAddress());
            restaurant.setAddress(savedAddress);
        }

        restaurant.setIsDeleted(false);
        return restaurantRepository.save(restaurant);
    }
}

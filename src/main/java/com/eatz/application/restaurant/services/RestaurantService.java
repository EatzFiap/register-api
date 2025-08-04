package com.eatz.application.restaurant.services;

import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.application.restaurant.usecases.CreateRestaurantUseCase;
import com.eatz.application.restaurant.usecases.DeleteRestaurantUseCase;
import com.eatz.application.restaurant.usecases.GetRestaurantUseCase;
import com.eatz.application.restaurant.usecases.UpdateRestaurantUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.infrastructure.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RestaurantService {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final SaveAddressUseCase saveAddressUseCase;
    private final JwtUtil jwtUtil;

    public RestaurantService(
            CreateRestaurantUseCase createRestaurantUseCase,
            GetRestaurantUseCase getRestaurantUseCase,
            DeleteRestaurantUseCase deleteRestaurantUseCase,
            UpdateRestaurantUseCase updateRestaurantUseCase,
            SaveAddressUseCase saveAddressUseCase,
            JwtUtil jwtUtil
    ) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.saveAddressUseCase = saveAddressUseCase;
        this.jwtUtil = jwtUtil;
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        if (restaurant.getAddress() != null) {
            Address savedAddress = saveAddressUseCase.execute(restaurant.getAddress());
            restaurant.setAddress(savedAddress);
        }
        restaurant.setCreatedAt(LocalDateTime.now());
        return createRestaurantUseCase.execute(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        return getRestaurantUseCase.findById(id);
    }

    public Restaurant updateRestaurant(Long id, Restaurant newData) {
        Restaurant existing = getRestaurantUseCase.findById(id);
        return updateRestaurantUseCase.execute(existing, newData);
    }

    public void deleteRestaurant(Long id) {
        Restaurant restaurant = getRestaurantUseCase.findById(id);
        deleteRestaurantUseCase.execute(restaurant);
    }
}

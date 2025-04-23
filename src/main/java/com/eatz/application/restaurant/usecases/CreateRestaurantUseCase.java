package com.eatz.application.restaurant.usecases;


import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateRestaurantUseCase {

    private final RestaurantRepository RestaurantRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateRestaurantUseCase(RestaurantRepository RestaurantRepository, PasswordEncoder passwordEncoder) {
        this.RestaurantRepository = RestaurantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Restaurant execute(Restaurant Restaurant) {
        if (Restaurant == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        String encodedPassword = passwordEncoder.encode(Restaurant.getPassword());
        Restaurant.setPassword(encodedPassword);

        return RestaurantRepository.save(Restaurant);
    }
}

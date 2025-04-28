package com.eatz.application.restaurantuser.usecases;


import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.domain.restaurantuser.RestaurantUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateRestaurantUser {

    private final RestaurantUserRepository RestaurantUserRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateRestaurantUser(RestaurantUserRepository RestaurantUserRepository, PasswordEncoder passwordEncoder) {
        this.RestaurantUserRepository = RestaurantUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RestaurantUser execute(RestaurantUser RestaurantUser) {
        if (RestaurantUser == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        String encodedPassword = passwordEncoder.encode(RestaurantUser.getPassword());
        RestaurantUser.setPassword(encodedPassword);

        return RestaurantUserRepository.save(RestaurantUser);
    }
}

package com.eatz.application.restaurantuser.usecases;


import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.domain.restaurantuser.RestaurantUserRepository;
import com.eatz.domain.restaurantuser.exceptions.RestaurantNotFoundException;

import java.util.List;
import java.util.UUID;

public class GetRestaurantUser {

    private final RestaurantUserRepository RestaurantUserRepository;

    public GetRestaurantUser(RestaurantUserRepository RestaurantUserRepository) {
        this.RestaurantUserRepository = RestaurantUserRepository;
    }

    public RestaurantUser execute(UUID id) throws RestaurantNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        return RestaurantUserRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("User not found with id: " + id));
    }

    public List<RestaurantUser> execute() throws RestaurantNotFoundException {
        List<RestaurantUser> restaurantUsers = RestaurantUserRepository.findAll();
        if (restaurantUsers.isEmpty()) {
            throw new RestaurantNotFoundException("No users found");
        }
        return restaurantUsers;
    }
}

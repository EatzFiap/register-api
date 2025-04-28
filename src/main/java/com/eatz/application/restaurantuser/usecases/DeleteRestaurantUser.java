package com.eatz.application.restaurantuser.usecases;


import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.domain.restaurantuser.RestaurantUserRepository;

import java.util.UUID;

public class DeleteRestaurantUser {
    private final RestaurantUserRepository restaurantUserRepository;

    public DeleteRestaurantUser(RestaurantUserRepository restaurantUserRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public void execute(RestaurantUser user) {
        user.setDeleted(true);
        restaurantUserRepository.save(user);
    }
}

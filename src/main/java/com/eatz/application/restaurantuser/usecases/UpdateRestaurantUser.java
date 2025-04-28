package com.eatz.application.restaurantuser.usecases;


import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.domain.restaurantuser.RestaurantUserRepository;

public class UpdateRestaurantUser {

    private final RestaurantUserRepository restaurantUserRepository;

    public UpdateRestaurantUser(RestaurantUserRepository restaurantUserRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public RestaurantUser execute(RestaurantUser user, RestaurantUser newData) {
        user.setName(newData.getName());
        user.setEmail(newData.getEmail());
        user.setPhone(newData.getPassword());
        user.setAddress(newData.getAddress());

        return restaurantUserRepository.save(user);
    }
}

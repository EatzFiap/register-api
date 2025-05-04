package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;

import java.util.List;


public class GetRestaurantUserUseCase {

    private final RestaurantUserRepository restaurantUserRepository;

    public GetRestaurantUserUseCase(RestaurantUserRepository restaurantUserRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public RestaurantUser execute(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ID não pode ser nulo.");

        return restaurantUserRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Usuário não encontrado com id: " + id));
    }

    public List<RestaurantUser> execute() {
        List<RestaurantUser> users = restaurantUserRepository.findAll();
        if (users.isEmpty())
            throw new RestaurantNotFoundException("Nenhum usuário encontrado.");
        return users;
    }
}
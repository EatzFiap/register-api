package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;


public class DeleteRestaurantUserUseCase {

    private final RestaurantUserRepository restaurantUserRepository;

    public DeleteRestaurantUserUseCase(RestaurantUserRepository restaurantUserRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public void execute(Long id) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");
        RestaurantUser user = restaurantUserRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Usuário não encontrado para exclusão."));
        user.setDeleted(true);
        restaurantUserRepository.save(user);
    }
}
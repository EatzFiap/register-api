package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;



public class UpdateRestaurantUserUseCase {

    private final RestaurantUserRepository restaurantUserRepository;

    public UpdateRestaurantUserUseCase(RestaurantUserRepository restaurantUserRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public RestaurantUser execute(Long id, RestaurantUser newData) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");
        if (newData == null) throw new IllegalArgumentException("Dados para atualização não podem ser nulos.");
        RestaurantUser user = restaurantUserRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Usuário não encontrado para atualização."));

        if (newData.getName() != null) user.setName(newData.getName());
        if (newData.getEmail() != null) user.setEmail(newData.getEmail());
        if (newData.getPhone() != null) user.setPhone(newData.getPhone());

        return restaurantUserRepository.save(user);
    }
}
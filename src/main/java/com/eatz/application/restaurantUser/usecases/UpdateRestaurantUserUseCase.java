package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;

import java.time.LocalDateTime;


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
        if (newData.getCpf() != null) user.setCpf(newData.getCpf());
        if (newData.getRole() != null) user.setRole(newData.getRole());
        if (newData.getAddress() != null) user.setAddress(newData.getAddress());
        user.setProfileImageUrl(newData.getProfileImageUrl());
        user.setUpdatedAt(LocalDateTime.now());

        return restaurantUserRepository.save(user);
    }
}
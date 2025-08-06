package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;

import java.time.LocalDateTime;


public class UpdateRestaurantUserUseCase {

    private final RestaurantUserRepository restaurantUserRepository;
    private final RestaurantUserTypeRepository restaurantUserTypeRepository;

    public UpdateRestaurantUserUseCase(RestaurantUserRepository restaurantUserRepository,
                                     RestaurantUserTypeRepository restaurantUserTypeRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
        this.restaurantUserTypeRepository = restaurantUserTypeRepository;
    }

    public RestaurantUser execute(Long id, RestaurantUser newData) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");
        if (newData == null) throw new IllegalArgumentException("Dados para atualização não podem ser nulos.");
        RestaurantUser user = restaurantUserRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Usuário não encontrado para atualização."));

        if (newData.getName() != null) user.setName(newData.getName());
        if (newData.getEmail() != null) {
            // Verificar se o email já existe (exceto para o usuário atual)
            if (!newData.getEmail().equals(user.getEmail()) && 
                restaurantUserRepository.existsByEmailAndIsDeletedFalse(newData.getEmail())) {
                throw new IllegalArgumentException("Este e-mail já está em uso.");
            }
            user.setEmail(newData.getEmail());
        }
        if (newData.getPhone() != null) user.setPhone(newData.getPhone());
        if (newData.getCpf() != null) user.setCpf(newData.getCpf());
        if (newData.getRole() != null) user.setRole(newData.getRole());
        if (newData.getAddress() != null) user.setAddress(newData.getAddress());
        user.setProfileImageUrl(newData.getProfileImageUrl());
        
        // Validar e atualizar o tipo de usuário se fornecido
        if (newData.getRestaurantUserTypeId() != null) {
            restaurantUserTypeRepository.findById(newData.getRestaurantUserTypeId())
                    .orElseThrow(() -> new RestaurantUserTypeNotFoundException(newData.getRestaurantUserTypeId()));
            user.setRestaurantUserTypeId(newData.getRestaurantUserTypeId());
        }
        
        user.setUpdatedAt(LocalDateTime.now());

        return restaurantUserRepository.save(user);
    }
}
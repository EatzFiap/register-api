package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
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

        return restaurantUserRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Usuário não encontrado com id: " + id));
    }

    public RestaurantUser execute(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }

        return restaurantUserRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomerNotFoundException("User not found with email: " + email));
    }


    public List<RestaurantUser> execute() {
        List<RestaurantUser> users = restaurantUserRepository.findAll();
        if (users.isEmpty())
            throw new RestaurantNotFoundException("Nenhum usuário encontrado.");
        return users;
    }
}
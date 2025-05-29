package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantUserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreateRestaurantUserUseCase {

    private final RestaurantUserRepository restaurantUserRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateRestaurantUserUseCase(RestaurantUserRepository restaurantUserRepository, PasswordEncoder passwordEncoder) {
        this.restaurantUserRepository = restaurantUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RestaurantUser execute(RestaurantUser restaurantUser) {
        if (restaurantUser == null)
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        if (restaurantUser.getEmail() == null || restaurantUser.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail é obrigatório.");

        if (restaurantUserRepository.existsByEmailAndIsDeletedFalse(restaurantUser.getEmail()))
            throw new RestaurantUserAlreadyExistsException("Este e-mail já está em uso.");

        String encodedPassword = passwordEncoder.encode(Objects.requireNonNull(restaurantUser.getPassword(), "Senha é obrigatória."));
        restaurantUser.setPassword(encodedPassword);
        restaurantUser.setCreatedAt(LocalDateTime.now());

        return restaurantUserRepository.save(restaurantUser);
    }
}
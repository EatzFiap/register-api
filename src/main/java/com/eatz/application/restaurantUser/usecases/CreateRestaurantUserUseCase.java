package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantUserAlreadyExistsException;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreateRestaurantUserUseCase {

    private final RestaurantUserRepository restaurantUserRepository;
    private final RestaurantUserTypeRepository restaurantUserTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateRestaurantUserUseCase(RestaurantUserRepository restaurantUserRepository, 
                                     RestaurantUserTypeRepository restaurantUserTypeRepository,
                                     PasswordEncoder passwordEncoder) {
        this.restaurantUserRepository = restaurantUserRepository;
        this.restaurantUserTypeRepository = restaurantUserTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RestaurantUser execute(RestaurantUser restaurantUser) {
        if (restaurantUser == null)
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        if (restaurantUser.getEmail() == null || restaurantUser.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail é obrigatório.");

        if (restaurantUserRepository.existsByEmailAndIsDeletedFalse(restaurantUser.getEmail()))
            throw new RestaurantUserAlreadyExistsException("Este e-mail já está em uso.");

        // Validar se o tipo de usuário existe
        if (restaurantUser.getRestaurantUserTypeId() != null) {
            restaurantUserTypeRepository.findById(restaurantUser.getRestaurantUserTypeId())
                    .orElseThrow(() -> new RestaurantUserTypeNotFoundException(restaurantUser.getRestaurantUserTypeId()));
        }

        String encodedPassword = passwordEncoder.encode(Objects.requireNonNull(restaurantUser.getPassword(), "Senha é obrigatória."));
        restaurantUser.setPassword(encodedPassword);
        restaurantUser.setCreatedAt(LocalDateTime.now());

        return restaurantUserRepository.save(restaurantUser);
    }
}
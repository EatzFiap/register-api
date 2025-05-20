package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.auth.AuthenticateUserUseCase;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticateRestaurantUserUseCase extends AuthenticateUserUseCase {

    private final JwtUtil jwtUtil;
    private final RestaurantUserRepository restaurantRepository;

    public AuthenticateRestaurantUserUseCase(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            RestaurantUserRepository restaurantRepository
    ) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public AuthenticationResponse execute(LoginRequest loginRequest) {
        if (!restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email()))
            throw new EntityNotFoundException("User not found");

        authenticate(loginRequest.email(), loginRequest.password());

        String role = restaurantRepository.findRoleByEmailAndIsDeletedFalse(loginRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("User role not found"));

        RestaurantRole restaurantRole;
        try {
            restaurantRole = RestaurantRole.valueOf(role);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user role: " + role);
        }

        String token = jwtUtil.generateRestaurantUserToken(loginRequest.email(), restaurantRole);

        return new AuthenticationResponse(
                token, "Bearer", jwtUtil.extractExpiration(token), loginRequest.email()
        );
    }

}
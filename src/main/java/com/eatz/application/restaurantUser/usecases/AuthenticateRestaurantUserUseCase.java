package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.presentation.web.restaurantUser.dto.AuthenticationResponse;
import com.eatz.presentation.web.restaurantUser.dto.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

public class AuthenticateRestaurantUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RestaurantUserRepository restaurantRepository;

    public AuthenticateRestaurantUserUseCase(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            RestaurantUserRepository restaurantRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.restaurantRepository = restaurantRepository;
    }

    public AuthenticationResponse execute(LoginRequest loginRequest) {
        if (!restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email()))
            throw new EntityNotFoundException("usuário");


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        String role = restaurantRepository.findRoleByEmailAndIsDeletedFalse(loginRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("usuário"));

        RestaurantRole restaurantRole;

        try {
            restaurantRole = RestaurantRole.valueOf(role);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user role: " + role);
        }

        String token = jwtUtil.generateRestaurantUserToken(loginRequest.email(), restaurantRole);

        return new AuthenticationResponse(
                token,
                "Bearer",
                jwtUtil.extractExpiration(token),
                loginRequest.email()
        );
    }
}
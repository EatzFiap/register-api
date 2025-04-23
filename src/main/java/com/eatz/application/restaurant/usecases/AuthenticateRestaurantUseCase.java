package com.eatz.application.restaurant.usecases;

import com.eatz.domain.restaurant.RestaurantRepository;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.presentation.web.restaurant.dto.AuthenticationResponse;
import com.eatz.presentation.web.restaurant.dto.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateRestaurantUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RestaurantRepository restaurantRepository;

    public AuthenticateRestaurantUseCase(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            RestaurantRepository restaurantRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.restaurantRepository = restaurantRepository;
    }

    public AuthenticationResponse execute(LoginRequest loginRequest) {
        if (!restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())) throw new EntityNotFoundException("usuário");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        String token = jwtUtil.generateToken(loginRequest.email());

        return new AuthenticationResponse(
                token,
                "Bearer",
                jwtUtil.extractExpiration(token),
                loginRequest.email()
        );
    }
}
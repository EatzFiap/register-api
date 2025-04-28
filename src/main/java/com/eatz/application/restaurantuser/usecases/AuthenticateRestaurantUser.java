package com.eatz.application.restaurantuser.usecases;

import com.eatz.domain.restaurantuser.RestaurantUserRepository;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.presentation.web.restaurantuser.dto.AuthenticationResponse;
import com.eatz.presentation.web.restaurantuser.dto.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateRestaurantUser {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RestaurantUserRepository restaurantUserRepository;

    public AuthenticateRestaurantUser(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            RestaurantUserRepository restaurantUserRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public AuthenticationResponse execute(LoginRequest loginRequest) {
        if (!restaurantUserRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())) throw new EntityNotFoundException("usuário");

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
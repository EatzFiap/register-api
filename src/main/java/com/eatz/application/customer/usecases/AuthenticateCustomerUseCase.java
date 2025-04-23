package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.CustomerRepository;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.presentation.web.customer.dto.AuthenticationResponse;
import com.eatz.presentation.web.customer.dto.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCustomerUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomerRepository customerRepository;

    public AuthenticateCustomerUseCase(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            CustomerRepository customerRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customerRepository = customerRepository;
    }

    public AuthenticationResponse execute(LoginRequest loginRequest) {
        if (!customerRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())) throw new EntityNotFoundException("usuário");

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
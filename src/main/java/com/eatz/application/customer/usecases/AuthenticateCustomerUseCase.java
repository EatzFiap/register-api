package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.CustomerRepository;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.auth.AuthenticateUserUseCase;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.exception.InvalidCredentialsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCustomerUseCase extends AuthenticateUserUseCase {

    private final JwtUtil jwtUtil;
    private final CustomerRepository customerRepository;

    public AuthenticateCustomerUseCase(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            CustomerRepository customerRepository
    ) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.customerRepository = customerRepository;
    }

    @Override
    public AuthenticationResponse execute(LoginRequest loginRequest) {
        if (!customerRepository.existsByEmailAndIsDeletedFalse(loginRequest.email()))
            throw new EntityNotFoundException("User not found");

        try {
            authenticate(loginRequest.email(), loginRequest.password());
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateCustomerUserToken(loginRequest.email());

        return new AuthenticationResponse(
                token, "Bearer", jwtUtil.extractExpiration(token), loginRequest.email()
        );
    }

}
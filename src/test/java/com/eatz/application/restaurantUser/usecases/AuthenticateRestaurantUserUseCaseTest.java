package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.exception.InvalidCredentialsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticateRestaurantUserUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RestaurantUserRepository restaurantRepository;

    @InjectMocks
    private AuthenticateRestaurantUserUseCase authenticateRestaurantUserUseCase;

    @Nested
    @DisplayName("Authenticate Restaurant User")
    class ExecuteAuthenticateRestaurantUser {

        @Test
        @DisplayName("Authenticates restaurant user successfully")
        void authenticatesRestaurantUserSuccessfully() {
            LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
            String role = "ADMIN";
            String token = "generatedToken";
            Date expirationDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour later

            when(restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(true);
            when(restaurantRepository.findRoleByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(Optional.of(role));
            when(jwtUtil.generateRestaurantUserToken(loginRequest.email(), RestaurantRole.ADMIN)).thenReturn(token);
            when(jwtUtil.extractExpiration(token)).thenReturn(expirationDate);

            AuthenticationResponse response = authenticateRestaurantUserUseCase.execute(loginRequest);

            assertEquals("Bearer", response.type());
            assertEquals(token, response.token());
            assertEquals(expirationDate, response.expiresAt());
            assertEquals(loginRequest.email(), response.username());
        }

        @Test
        @DisplayName("Throws exception when user does not exist")
        void throwsExceptionWhenUserDoesNotExist() {
            LoginRequest loginRequest = new LoginRequest("nonexistent@example.com", "password");

            when(restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(false);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> authenticateRestaurantUserUseCase.execute(loginRequest));

            assertEquals("User not found", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when credentials are invalid")
        void throwsExceptionWhenCredentialsAreInvalid() {
            LoginRequest loginRequest = new LoginRequest("user@example.com", "wrongpassword");

            when(restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(true);
            doThrow(new BadCredentialsException("Bad credentials"))
                    .when(authenticationManager).authenticate(any());

            InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
                    () -> authenticateRestaurantUserUseCase.execute(loginRequest));

            assertNotNull(exception);
        }

        @Test
        @DisplayName("Throws exception when user role is not found")
        void throwsExceptionWhenUserRoleIsNotFound() {
            LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

            when(restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(true);
            when(restaurantRepository.findRoleByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> authenticateRestaurantUserUseCase.execute(loginRequest));

            assertEquals("User role not found", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user role is invalid")
        void throwsExceptionWhenUserRoleIsInvalid() {
            LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

            when(restaurantRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(true);
            when(restaurantRepository.findRoleByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(Optional.of("INVALID_ROLE"));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> authenticateRestaurantUserUseCase.execute(loginRequest));

            assertEquals("Invalid user role: INVALID_ROLE", exception.getMessage());
        }

    }

}
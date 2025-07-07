package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.CustomerRepository;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.exceptions.InvalidCredentialsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateCustomerUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticateCustomerUseCase authenticateCustomerUseCase;

    @Nested
    class Execute {

        @Test
        @DisplayName("Returns response for valid credentials")
        void returnsAuthenticationResponseForValidCredentials() {
            LoginRequest loginRequest = new LoginRequest("valid@example.com", "validPassword");
            String token = "token123";
            Date expirationDate = new Date(System.currentTimeMillis() + 3600 * 1000);

            when(customerRepository.existsByEmailAndIsDeletedFalse(loginRequest.email())).thenReturn(true);
            when(jwtUtil.generateCustomerUserToken(loginRequest.email())).thenReturn(token);
            when(jwtUtil.extractExpiration(token)).thenReturn(expirationDate);

            AuthenticationResponse response = authenticateCustomerUseCase.execute(loginRequest);

            assertEquals(token, response.token());
            assertEquals("Bearer", response.type());
            assertEquals(expirationDate, response.expiresAt());
            assertEquals(loginRequest.email(), response.username());
        }

        @ParameterizedTest
        @DisplayName("Throws EntityNotFoundException when customer does not exist")
        @CsvSource({
                "nonexistent@example.com, User not found",
                "deleted@example.com, User not found"
        })
        void throwsExceptionWhenCustomerDoesNotExist(String email, String expectedMessage) {
            LoginRequest loginRequest = new LoginRequest(email, "password");

            when(customerRepository.existsByEmailAndIsDeletedFalse(email)).thenReturn(false);

            EntityNotFoundException exception =
                    assertThrows(EntityNotFoundException.class, () -> authenticateCustomerUseCase.execute(loginRequest));

            assertEquals(expectedMessage, exception.getMessage());
        }

        @Test
        @DisplayName("Throws InvalidCredentialsException when credentials are invalid")
        void throwsExceptionWhenCredentialsAreInvalid() {
            String email = "valid@email.com";
            String password = "invalidPassword";
            LoginRequest loginRequest = new LoginRequest(email, password);

            when(customerRepository.existsByEmailAndIsDeletedFalse(email)).thenReturn(true);
            AuthenticateCustomerUseCase useCase = new AuthenticateCustomerUseCase(authenticationManager, jwtUtil, customerRepository) {
                @Override
                protected void authenticate(String email, String password) {
                    throw new BadCredentialsException("Mocked");
                }
            };

            InvalidCredentialsException exception =
                    assertThrows(InvalidCredentialsException.class, () -> useCase.execute(loginRequest));
            assertEquals("Invalid credentials. Check your email and password.", exception.getMessage());
        }

    }

}
package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantUserAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUserUseCaseTest {

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateRestaurantUserUseCase createRestaurantUserUseCase;

    @Nested
    @DisplayName("Execute Create Restaurant")
    class ExecuteCreateRestaurant {

        @Test
        @DisplayName("Creates restaurant user successfully")
        void createsRestaurantUserSuccessfully() {
            RestaurantUser restaurantUser = new RestaurantUser();
            restaurantUser.setEmail("test@example.com");
            restaurantUser.setPassword("password");

            String encodedPassword = "encodedPassword";
            when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
            when(restaurantUserRepository.save(any(RestaurantUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

            RestaurantUser result = createRestaurantUserUseCase.execute(restaurantUser);

            assertEquals("test@example.com", result.getEmail());
            assertEquals(encodedPassword, result.getPassword());
            assertNotNull(result.getCreatedAt());
        }

        @Test
        @DisplayName("Throws exception when restaurant user is null")
        void throwsExceptionWhenRestaurantUserIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> createRestaurantUserUseCase.execute(null));

            assertEquals("Usuário não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when email is null or blank")
        void throwsExceptionWhenEmailIsNullOrBlank() {
            RestaurantUser restaurantUser = new RestaurantUser();
            restaurantUser.setPassword("password");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> createRestaurantUserUseCase.execute(restaurantUser));

            assertEquals("E-mail é obrigatório.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when email is already in use")
        void throwsExceptionWhenEmailIsAlreadyInUse() {
            RestaurantUser restaurantUser = new RestaurantUser();
            restaurantUser.setEmail("test@example.com");
            restaurantUser.setPassword("password");

            when(restaurantUserRepository.existsByEmailAndIsDeletedFalse("test@example.com")).thenReturn(true);

            RestaurantUserAlreadyExistsException exception = assertThrows(RestaurantUserAlreadyExistsException.class,
                    () -> createRestaurantUserUseCase.execute(restaurantUser));

            assertEquals("Este e-mail já está em uso.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when password is null")
        void throwsExceptionWhenPasswordIsNull() {
            RestaurantUser restaurantUser = new RestaurantUser();
            restaurantUser.setEmail("test@example.com");

            NullPointerException exception = assertThrows(NullPointerException.class,
                    () -> createRestaurantUserUseCase.execute(restaurantUser));

            assertEquals("Senha é obrigatória.", exception.getMessage());
        }

    }

}
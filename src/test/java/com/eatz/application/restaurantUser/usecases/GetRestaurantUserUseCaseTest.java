package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRestaurantUserUseCaseTest {

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    @InjectMocks
    private GetRestaurantUserUseCase getRestaurantUserUseCase;

    @Nested
    @DisplayName("Get Restaurant User By ID")
    class ExecuteGetRestaurantUserById {

        @Test
        @DisplayName("Returns restaurant user successfully")
        void returnsRestaurantUserSuccessfully() {
            Long id = 1L;
            RestaurantUser user = new RestaurantUser();
            user.setId(id);
            user.setEmail("test@example.com");

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(user));

            RestaurantUser result = getRestaurantUserUseCase.execute(id);

            assertEquals(id, result.getId());
            assertEquals("test@example.com", result.getEmail());
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> getRestaurantUserUseCase.execute((Long) null));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long id = 1L;

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                    () -> getRestaurantUserUseCase.execute(id));

            assertEquals("Usuário não encontrado com id: " + id, exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Get Restaurant User By Email")
    class ExecuteGetRestaurantUserByEmail {

        @Test
        @DisplayName("Returns restaurant user successfully by email")
        void returnsRestaurantUserSuccessfullyByEmail() {
            String email = "test@example.com";
            RestaurantUser user = new RestaurantUser();
            user.setEmail(email);

            when(restaurantUserRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.of(user));

            RestaurantUser result = getRestaurantUserUseCase.execute(email);

            assertEquals(email, result.getEmail());
        }

        @Test
        @DisplayName("Throws exception when email is null")
        void throwsExceptionWhenEmailIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> getRestaurantUserUseCase.execute((String) null));

            assertEquals("Email cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user is not found by email")
        void throwsExceptionWhenUserIsNotFoundByEmail() {
            String email = "notfound@example.com";

            when(restaurantUserRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.empty());

            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                    () -> getRestaurantUserUseCase.execute(email));

            assertEquals("User not found with email: " + email, exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Get All Restaurant Users")
    class ExecuteGetAllRestaurantUsers {

        @Test
        @DisplayName("Returns all restaurant users successfully")
        void returnsAllRestaurantUsersSuccessfully() {
            RestaurantUser user1 = new RestaurantUser();
            user1.setId(1L);
            user1.setEmail("user1@example.com");

            RestaurantUser user2 = new RestaurantUser();
            user2.setId(2L);
            user2.setEmail("user2@example.com");

            when(restaurantUserRepository.findAll()).thenReturn(List.of(user1, user2));

            List<RestaurantUser> result = getRestaurantUserUseCase.execute();

            assertEquals(2, result.size());
            assertEquals("user1@example.com", result.get(0).getEmail());
            assertEquals("user2@example.com", result.get(1).getEmail());
        }

        @Test
        @DisplayName("Throws exception when no users are found")
        void throwsExceptionWhenNoUsersAreFound() {
            when(restaurantUserRepository.findAll()).thenReturn(List.of());

            RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                    () -> getRestaurantUserUseCase.execute());

            assertEquals("Nenhum usuário encontrado.", exception.getMessage());
        }

    }

}
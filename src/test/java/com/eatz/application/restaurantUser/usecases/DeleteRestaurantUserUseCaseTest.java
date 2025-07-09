package com.eatz.application.restaurantUser.usecases;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUserUseCaseTest {

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    @InjectMocks
    private DeleteRestaurantUserUseCase deleteRestaurantUserUseCase;

    @Nested
    @DisplayName("Execute Delete Restaurant User by ID")
    class ExecuteById {

        @Test
        @DisplayName("Marks user as deleted successfully")
        void marksUserAsDeletedSuccessfully() {
            Long id = 1L;
            RestaurantUser user = new RestaurantUser();
            user.setId(id);
            user.setDeleted(false);

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(user));
            when(restaurantUserRepository.save(any(RestaurantUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

            deleteRestaurantUserUseCase.execute(id);

            assertTrue(user.isDeleted());
            verify(restaurantUserRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> deleteRestaurantUserUseCase.execute(null));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long id = 1L;

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                    () -> deleteRestaurantUserUseCase.execute(id));

            assertEquals("Usuário não encontrado para exclusão.", exception.getMessage());
        }

    }

}
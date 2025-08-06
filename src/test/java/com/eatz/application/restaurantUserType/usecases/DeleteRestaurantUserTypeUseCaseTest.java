package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeInUseException;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUserTypeUseCaseTest {

    @Mock
    private RestaurantUserTypeRepository repository;

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    @InjectMocks
    private DeleteRestaurantUserTypeUseCase deleteRestaurantUserTypeUseCase;

    @Nested
    @DisplayName("Delete Restaurant User Type")
    class DeleteRestaurantUserType {

        @Test
        @DisplayName("Deletes RestaurantUserType successfully when not in use")
        void deletesRestaurantUserTypeSuccessfullyWhenNotInUse() {
            Long id = 1L;
            RestaurantUserType restaurantUserType = new RestaurantUserType();
            restaurantUserType.setId(id);

            when(repository.findById(id)).thenReturn(Optional.of(restaurantUserType));
            when(restaurantUserRepository.existsByRestaurantUserTypeIdAndIsDeletedFalse(id)).thenReturn(false);

            deleteRestaurantUserTypeUseCase.execute(id);

            assertTrue(restaurantUserType.isDeleted());
            verify(repository).findById(id);
            verify(restaurantUserRepository).existsByRestaurantUserTypeIdAndIsDeletedFalse(id);
            verify(repository).save(restaurantUserType);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    deleteRestaurantUserTypeUseCase.execute(null)
            );

            assertEquals("ID cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when RestaurantUserType is not found")
        void throwsExceptionWhenRestaurantUserTypeIsNotFound() {
            Long id = 1L;

            when(repository.findById(id)).thenReturn(Optional.empty());

            RestaurantUserTypeNotFoundException exception = assertThrows(RestaurantUserTypeNotFoundException.class, () ->
                    deleteRestaurantUserTypeUseCase.execute(id)
            );

            assertEquals("Restaurant user type not found with id: " + id, exception.getMessage());
            verify(repository).findById(id);
        }

        @Test
        @DisplayName("Throws exception when RestaurantUserType is in use")
        void throwsExceptionWhenRestaurantUserTypeIsInUse() {
            Long id = 1L;
            RestaurantUserType restaurantUserType = new RestaurantUserType();
            restaurantUserType.setId(id);

            when(repository.findById(id)).thenReturn(Optional.of(restaurantUserType));
            when(restaurantUserRepository.existsByRestaurantUserTypeIdAndIsDeletedFalse(id)).thenReturn(true);

            RestaurantUserTypeInUseException exception = assertThrows(RestaurantUserTypeInUseException.class, () ->
                    deleteRestaurantUserTypeUseCase.execute(id)
            );

            assertEquals("Restaurant user type cannot be deleted. There are users associated with type ID: " + id, exception.getMessage());
            verify(repository).findById(id);
            verify(restaurantUserRepository).existsByRestaurantUserTypeIdAndIsDeletedFalse(id);
        }

    }

}
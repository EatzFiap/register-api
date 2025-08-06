package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeAlreadyExistsException;
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
class UpdateRestaurantUserTypeUseCaseTest {

    @Mock
    private RestaurantUserTypeRepository repository;

    @InjectMocks
    private UpdateRestaurantUserTypeUseCase updateRestaurantUserTypeUseCase;

    @Nested
    @DisplayName("Update Restaurant User Type")
    class UpdateRestaurantUserType {

        @Test
        @DisplayName("Updates RestaurantUserType successfully when valid data is provided")
        void updatesRestaurantUserTypeSuccessfullyWhenValidDataIsProvided() {
            Long id = 1L;
            RestaurantUserType existingType = new RestaurantUserType();
            existingType.setId(id);
            existingType.setName("Old Name");
            existingType.setDescription("Old Description");

            RestaurantUserType newData = new RestaurantUserType();
            newData.setName("New Name");
            newData.setDescription("New Description");

            when(repository.findById(id)).thenReturn(Optional.of(existingType));
            when(repository.existsByName(newData.getName())).thenReturn(false);
            when(repository.save(existingType)).thenReturn(existingType);

            RestaurantUserType result = updateRestaurantUserTypeUseCase.execute(id, newData);

            assertNotNull(result);
            assertEquals("New Name", result.getName());
            assertEquals("New Description", result.getDescription());
            verify(repository).findById(id);
            verify(repository).existsByName(newData.getName());
            verify(repository).save(existingType);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            RestaurantUserType newData = new RestaurantUserType();
            newData.setName("New Name");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    updateRestaurantUserTypeUseCase.execute(null, newData)
            );

            assertEquals("ID cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when new data is null")
        void throwsExceptionWhenNewDataIsNull() {
            Long id = 1L;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    updateRestaurantUserTypeUseCase.execute(id, null)
            );

            assertEquals("New data cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when RestaurantUserType is not found")
        void throwsExceptionWhenRestaurantUserTypeIsNotFound() {
            Long id = 1L;
            RestaurantUserType newData = new RestaurantUserType();
            newData.setName("New Name");

            when(repository.findById(id)).thenReturn(Optional.empty());

            RestaurantUserTypeNotFoundException exception = assertThrows(RestaurantUserTypeNotFoundException.class, () ->
                    updateRestaurantUserTypeUseCase.execute(id, newData)
            );

            assertEquals("Restaurant user type not found with id: " + id, exception.getMessage());
            verify(repository).findById(id);
        }

        @Test
        @DisplayName("Throws exception when new name already exists")
        void throwsExceptionWhenNewNameAlreadyExists() {
            Long id = 1L;
            RestaurantUserType existingType = new RestaurantUserType();
            existingType.setId(id);
            existingType.setName("Old Name");

            RestaurantUserType newData = new RestaurantUserType();
            newData.setName("Existing Name");

            when(repository.findById(id)).thenReturn(Optional.of(existingType));
            when(repository.existsByName(newData.getName())).thenReturn(true);

            RestaurantUserTypeAlreadyExistsException exception = assertThrows(RestaurantUserTypeAlreadyExistsException.class, () ->
                    updateRestaurantUserTypeUseCase.execute(id, newData)
            );

            assertEquals("Existing Name", exception.getMessage());
            verify(repository).findById(id);
            verify(repository).existsByName(newData.getName());
        }

    }

}
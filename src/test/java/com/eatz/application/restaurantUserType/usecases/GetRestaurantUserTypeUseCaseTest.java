package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRestaurantUserTypeUseCaseTest {

    @Mock
    private RestaurantUserTypeRepository repository;

    @InjectMocks
    private GetRestaurantUserTypeUseCase getRestaurantUserTypeUseCase;

    @Nested
    @DisplayName("Get Restaurant User Type by ID")
    class GetRestaurantUserTypeById {

        @Test
        @DisplayName("Returns RestaurantUserType successfully when ID exists")
        void returnsRestaurantUserTypeSuccessfullyWhenIdExists() {
            Long id = 1L;
            RestaurantUserType restaurantUserType = new RestaurantUserType();
            restaurantUserType.setId(id);

            when(repository.findById(id)).thenReturn(Optional.of(restaurantUserType));

            RestaurantUserType result = getRestaurantUserTypeUseCase.executeById(id);

            assertNotNull(result);
            assertEquals(id, result.getId());
            verify(repository, times(1)).findById(id);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    getRestaurantUserTypeUseCase.executeById(null)
            );

            assertEquals("ID cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when RestaurantUserType is not found by ID")
        void throwsExceptionWhenRestaurantUserTypeIsNotFoundById() {
            Long id = 1L;

            when(repository.findById(id)).thenReturn(Optional.empty());

            RestaurantUserTypeNotFoundException exception = assertThrows(RestaurantUserTypeNotFoundException.class, () ->
                    getRestaurantUserTypeUseCase.executeById(id)
            );

            assertEquals("Restaurant user type not found with id: " + id, exception.getMessage());
            verify(repository, times(1)).findById(id);
        }

    }

    @Nested
    @DisplayName("Find all Restaurant User Types")
    class FindAllRestaurantUserTypes {

        @Test
        @DisplayName("Returns all RestaurantUserTypes successfully")
        void returnsAllRestaurantUserTypesSuccessfully() {
            List<RestaurantUserType> restaurantUserTypes = List.of(new RestaurantUserType(), new RestaurantUserType());

            when(repository.findAll()).thenReturn(restaurantUserTypes);

            List<RestaurantUserType> result = getRestaurantUserTypeUseCase.executeFindAll();

            assertNotNull(result);
            assertEquals(restaurantUserTypes.size(), result.size());
            assertThat(result).usingRecursiveComparison().isEqualTo(restaurantUserTypes);
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Returns empty list when no RestaurantUserTypes are found")
        void returnsEmptyListWhenNoRestaurantUserTypesAreFound() {
            when(repository.findAll()).thenReturn(List.of());

            List<RestaurantUserType> result = getRestaurantUserTypeUseCase.executeFindAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(repository, times(1)).findAll();
        }

    }

}
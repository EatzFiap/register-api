package com.eatz.application.restaurant.usecases;

import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private GetRestaurantUseCase getRestaurantUseCase;

    @Nested
    @DisplayName("Find Restaurant By ID")
    class FindRestaurantById {

        @Test
        @DisplayName("Returns restaurant successfully when ID exists")
        void returnsRestaurantSuccessfullyWhenIdExists() {
            Long id = 1L;
            Restaurant restaurant = new Restaurant();
            restaurant.setId(id);

            when(repository.findById(id)).thenReturn(Optional.of(restaurant));

            Restaurant result = getRestaurantUseCase.findById(id);

            assertNotNull(result);
            assertEquals(id, result.getId());
            verify(repository, times(1)).findById(id);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> getRestaurantUseCase.findById(null));

            assertEquals("Restaurant ID cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when restaurant is not found")
        void throwsExceptionWhenRestaurantIsNotFound() {
            Long id = 1L;

            when(repository.findById(id)).thenReturn(Optional.empty());

            RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                    () -> getRestaurantUseCase.findById(id));

            assertEquals("Restaurant not found with id: " + id, exception.getMessage());
            verify(repository, times(1)).findById(id);
        }
    }

    @Nested
    @DisplayName("Find All Restaurants")
    class FindAllRestaurants {

        @Test
        @DisplayName("Returns all restaurants successfully")
        void returnsAllRestaurantsSuccessfully() {
            List<Restaurant> restaurants = List.of(new Restaurant(), new Restaurant());

            when(repository.findAll()).thenReturn(restaurants);

            List<Restaurant> result = getRestaurantUseCase.findAll();

            assertNotNull(result);
            assertEquals(restaurants.size(), result.size());
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Returns empty list when no restaurants are found")
        void returnsEmptyListWhenNoRestaurantsAreFound() {
            when(repository.findAll()).thenReturn(List.of());

            List<Restaurant> result = getRestaurantUseCase.findAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(repository, times(1)).findAll();
        }

    }

}
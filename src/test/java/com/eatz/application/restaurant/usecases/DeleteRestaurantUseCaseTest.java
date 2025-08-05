package com.eatz.application.restaurant.usecases;

import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @Nested
    @DisplayName("Execute Delete Restaurant")
    class ExecuteDeleteRestaurant {

        @Test
        @DisplayName("Deletes restaurant successfully when valid restaurant is provided")
        void deletesRestaurantSuccessfullyWhenValidRestaurantIsProvided() {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(1L);

            doNothing().when(repository).delete(restaurant);

            deleteRestaurantUseCase.execute(restaurant);

            verify(repository, times(1)).delete(restaurant);
        }

    }

}
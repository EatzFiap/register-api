package com.eatz.application.restaurant.usecases;

import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.domain.address.Address;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private SaveAddressUseCase saveAddressUseCase;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Nested
    @DisplayName("Execute Create Restaurant")
    class ExecuteCreateRestaurant {

        @Test
        @DisplayName("Creates restaurant successfully when valid data is provided")
        void createsRestaurantSuccessfullyWhenValidDataIsProvided() {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Test Restaurant");
            Address address = new Address();
            restaurant.setAddress(address);

            Address savedAddress = new Address();
            when(saveAddressUseCase.execute(address)).thenReturn(savedAddress);
            when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Restaurant result = createRestaurantUseCase.execute(restaurant);

            assertNotNull(result);
            assertEquals("Test Restaurant", result.getName());
            assertEquals(savedAddress, result.getAddress());
            assertFalse(result.getIsDeleted());
        }

        @Test
        @DisplayName("Throws exception when restaurant is null")
        void throwsExceptionWhenRestaurantIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> createRestaurantUseCase.execute(null));

            assertEquals("Restaurant cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when restaurant name is null or blank")
        void throwsExceptionWhenRestaurantNameIsNullOrBlank() {
            Restaurant restaurant = new Restaurant();

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> createRestaurantUseCase.execute(restaurant));

            assertEquals("Restaurant name is required", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when restaurant name already exists")
        void throwsExceptionWhenRestaurantNameAlreadyExists() {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Existing Restaurant");

            when(restaurantRepository.existsByNameAndIsDeletedFalse("Existing Restaurant")).thenReturn(true);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> createRestaurantUseCase.execute(restaurant));

            assertEquals("A restaurant with this name already exists", exception.getMessage());
        }

        @Test
        @DisplayName("Creates restaurant successfully when address is null")
        void createsRestaurantSuccessfullyWhenAddressIsNull() {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Test Restaurant");

            when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Restaurant result = createRestaurantUseCase.execute(restaurant);

            assertNotNull(result);
            assertEquals("Test Restaurant", result.getName());
            assertNull(result.getAddress());
            assertFalse(result.getIsDeleted());
        }

    }

}
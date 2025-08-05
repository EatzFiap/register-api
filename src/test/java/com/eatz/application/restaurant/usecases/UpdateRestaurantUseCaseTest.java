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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private SaveAddressUseCase saveAddressUseCase;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Nested
    @DisplayName("Update Restaurant")
    class UpdateRestaurant {

        @Test
        @DisplayName("Updates restaurant successfully with valid data")
        void updatesRestaurantSuccessfullyWithValidData() {
            Restaurant existing = new Restaurant();
            existing.setId(1L);
            existing.setName("Old Name");
            existing.setPhone("123456789");
            Address existingAddress = new Address();
            existing.setAddress(existingAddress);

            Restaurant newData = new Restaurant();
            newData.setName("New Name");
            newData.setPhone("987654321");
            Address newAddress = new Address();
            newData.setAddress(newAddress);

            Address savedAddress = new Address();
            when(saveAddressUseCase.execute(newAddress)).thenReturn(savedAddress);
            when(restaurantRepository.save(existing)).thenReturn(existing);

            Restaurant result = updateRestaurantUseCase.execute(existing, newData);

            assertNotNull(result);
            assertEquals("New Name", result.getName());
            assertEquals("987654321", result.getPhone());
            assertEquals(savedAddress, result.getAddress());
            verify(saveAddressUseCase, times(1)).execute(newAddress);
            verify(restaurantRepository, times(1)).save(existing);
        }

        @Test
        @DisplayName("Throws exception when existing restaurant is null")
        void throwsExceptionWhenExistingRestaurantIsNull() {
            Restaurant newData = new Restaurant();

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> updateRestaurantUseCase.execute(null, newData));

            assertEquals("Restaurant data cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when new data is null")
        void throwsExceptionWhenNewDataIsNull() {
            Restaurant existing = new Restaurant();

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> updateRestaurantUseCase.execute(existing, null));

            assertEquals("Restaurant data cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Updates restaurant successfully when address is null")
        void updatesRestaurantSuccessfullyWhenAddressIsNull() {
            Restaurant existing = new Restaurant();
            existing.setId(1L);
            existing.setName("Old Name");

            Restaurant newData = new Restaurant();
            newData.setName("New Name");

            when(restaurantRepository.save(existing)).thenReturn(existing);

            Restaurant result = updateRestaurantUseCase.execute(existing, newData);

            assertNotNull(result);
            assertEquals("New Name", result.getName());
            assertNull(result.getAddress());
            verify(restaurantRepository, times(1)).save(existing);
        }

    }

}
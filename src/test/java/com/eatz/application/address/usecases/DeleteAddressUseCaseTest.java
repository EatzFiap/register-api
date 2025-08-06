package com.eatz.application.address.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.AddressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private DeleteAddressUseCase deleteAddressUseCase;

    @Nested
    @DisplayName("Execute Method Tests")
    class Execute {

        @Test
        @DisplayName("Marks address as deleted and saves it")
        void marksAddressAsDeletedAndSavesIt() {
            Address address = new Address();
            address.setDeleted(false);
            address.setId(1L);

            when(addressRepository.save(address)).thenReturn(address);

            Address result = deleteAddressUseCase.execute(address);

            assertTrue(result.isDeleted());
            assertEquals(1L, result.getId());
            verify(addressRepository).save(address);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when address is null")
        void throwsExceptionWhenAddressIsNull() {
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> deleteAddressUseCase.execute(null));
            assertEquals("Endereço não pode ser nulo.", exception.getMessage());
        }

    }

}
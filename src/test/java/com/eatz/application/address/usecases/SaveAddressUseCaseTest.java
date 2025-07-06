package com.eatz.application.address.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.AddressRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private SaveAddressUseCase saveAddressUseCase;

    @Nested
    class ExecuteTests {

        @Test
        void savesValidAddressSuccessfully() {
            Address address = new Address();
            address.setId(1L);
            address.setStreet("Rua das Laranjeiras");
            address.setCity("São Paulo");
            address.setState("SP");
            address.setZipCode("09010-000");
            address.setDeleted(false);
            address.setNeighbourhood("Centro");
            address.setNumber("456");
            address.setComplement("Apto 1");
            address.setCreatedAt(String.valueOf(LocalDateTime.now()));

            when(addressRepository.save(address)).thenReturn(address);

            Address result = saveAddressUseCase.execute(address);

            verify(addressRepository).save(address);
            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Rua das Laranjeiras", result.getStreet());
            assertEquals("São Paulo", result.getCity());
            assertEquals("SP", result.getState());
            assertEquals("09010-000", result.getZipCode());
            assertEquals("Centro", result.getNeighbourhood());
            assertEquals("456", result.getNumber());
            assertEquals("Apto 1", result.getComplement());
            assertNotNull(result.getCreatedAt());
            assertNull(result.getUpdatedAt());
        }

        @Test
        void throwsExceptionWhenAddressIsNull() {
            IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> saveAddressUseCase.execute(null));
            assertEquals("Endereço não pode ser nulo.", exception.getMessage());
        }

    }

}
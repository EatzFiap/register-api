package com.eatz.application.customerAddresses.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.customerAddresses.CustomerAddress;
import com.eatz.domain.customerAddresses.CustomerAddressRepository;
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
class AssociateAddressToCustomerUseCaseTest {

    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @InjectMocks
    private AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase;

    @Nested
    @DisplayName("Execute Associate Address To Customer")
    class ExecuteAssociateAddressToCustomer {

        @Test
        @DisplayName("Associates address to customer successfully")
        void associatesAddressToCustomerSuccessfully() {
            Long customerId = 1L;
            Address address = new Address();
            address.setId(1L);

            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setCustomerId(customerId);
            customerAddress.setAddressId(address.getId());
            customerAddress.setNickname("Home");
            customerAddress.setDefault(true);

            when(customerAddressRepository.save(any(CustomerAddress.class))).thenReturn(customerAddress);

            CustomerAddress result = associateAddressToCustomerUseCase.execute(customerId, address, "Home", true);

            assertEquals(customerId, result.getCustomerId());
            assertEquals(address.getId(), result.getAddressId());
            assertEquals("Home", result.getNickname());
            assertTrue(result.isDefault());
        }

        @Test
        @DisplayName("Throws exception when customer ID is null")
        void throwsExceptionWhenCustomerIdIsNull() {
            Address address = new Address();
            address.setId(1L);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> associateAddressToCustomerUseCase.execute(null, address, "Home", true));

            assertEquals("ID do cliente não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when address ID is null")
        void throwsExceptionWhenAddressIdIsNull() {
            Address address = new Address();

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> associateAddressToCustomerUseCase.execute(1L, address, "Home", true));

            assertEquals("ID do endereço não pode ser nulo.", exception.getMessage());
        }

    }

}
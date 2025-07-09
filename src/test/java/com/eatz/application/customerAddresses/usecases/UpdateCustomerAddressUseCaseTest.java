package com.eatz.application.customerAddresses.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.AddressRepository;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customerAddresses.CustomerAddress;
import com.eatz.domain.customerAddresses.CustomerAddressRepository;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.shared.exceptions.AddressNotFoundException;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @InjectMocks
    private UpdateCustomerAddressUseCase updateCustomerAddressUseCase;

    @Nested
    @DisplayName("Execute Update Customer Address")
    class ExecuteUpdateCustomerAddress {

        @Test
        @DisplayName("Updates customer address and base address successfully")
        void updatesCustomerAndBaseAddressSuccessfully() {
            Customer customer = new Customer();
            customer.setId(1L);

            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setAddressId(1L);

            AddressRequest request = new AddressRequest();
            request.setNickname("Home");
            request.setDefaultAddress(true);
            request.setStreet("Street");
            request.setNumber("123");
            request.setComplement("Apt 1");
            request.setCity("City");
            request.setNeighbourhood("Neighbourhood");
            request.setState("State");
            request.setZipCode("12345");

            Address address = new Address();
            address.setId(1L);

            when(customerAddressRepository.findByCustomerId(customer.getId())).thenReturn(List.of(customerAddress));
            when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

            updateCustomerAddressUseCase.execute(customer, 1L, request);

            verify(customerAddressRepository, times(1)).save(argThat(savedCustomerAddress ->
                    savedCustomerAddress.getNickname().equals("Home") &&
                            savedCustomerAddress.isDefault() &&
                            savedCustomerAddress.getUpdatedAt() != null));

            verify(addressRepository, times(1)).save(argThat(savedAddress ->
                    savedAddress.getStreet().equals("Street") &&
                            savedAddress.getNumber().equals("123") &&
                            savedAddress.getComplement().equals("Apt 1") &&
                            savedAddress.getCity().equals("City") &&
                            savedAddress.getNeighbourhood().equals("Neighbourhood") &&
                            savedAddress.getState().equals("State") &&
                            savedAddress.getZipCode().equals("12345") &&
                            savedAddress.getUpdatedAt() != null));
        }

        @Test
        @DisplayName("Throws exception when customer has no addresses")
        void throwsExceptionWhenCustomerHasNoAddresses() {
            Customer customer = new Customer();
            customer.setId(1L);

            AddressRequest request = new AddressRequest();

            when(customerAddressRepository.findByCustomerId(customer.getId())).thenReturn(List.of());

            AddressNotFoundException exception = assertThrows(AddressNotFoundException.class,
                    () -> updateCustomerAddressUseCase.execute(customer, 1L, request));

            assertEquals("Nenhum endereço encontrado para o cliente", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when address is not found in customer's address list")
        void throwsExceptionWhenAddressNotFoundInCustomerAddressList() {
            Customer customer = new Customer();
            customer.setId(1L);

            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setAddressId(2L);

            AddressRequest request = new AddressRequest();

            when(customerAddressRepository.findByCustomerId(customer.getId())).thenReturn(List.of(customerAddress));

            AddressNotFoundException exception = assertThrows(AddressNotFoundException.class,
                    () -> updateCustomerAddressUseCase.execute(customer, 1L, request));

            assertEquals("Endereço não encontrado na lista de endereços do cliente", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when base address is not found")
        void throwsExceptionWhenBaseAddressNotFound() {
            Customer customer = new Customer();
            customer.setId(1L);

            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setAddressId(1L);

            AddressRequest request = new AddressRequest();
            request.setNickname("Home");
            request.setDefaultAddress(true);

            when(customerAddressRepository.findByCustomerId(customer.getId())).thenReturn(List.of(customerAddress));
            when(addressRepository.findById(1L)).thenReturn(Optional.empty());

            AddressNotFoundException exception = assertThrows(AddressNotFoundException.class,
                    () -> updateCustomerAddressUseCase.execute(customer, 1L, request));

            assertEquals("Endereço base não encontrado", exception.getMessage());
        }

    }

}
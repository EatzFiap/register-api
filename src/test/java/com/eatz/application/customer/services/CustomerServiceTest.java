package com.eatz.application.customer.services;

import com.eatz.application.address.usecases.DeleteAddressUseCase;
import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.application.customer.usecases.CreateCustomerUseCase;
import com.eatz.application.customer.usecases.DeleteCustomerUseCase;
import com.eatz.application.customer.usecases.GetCustomerUseCase;
import com.eatz.application.customer.usecases.UpdateCustomerUseCase;
import com.eatz.application.customerAddresses.usecases.AssociateAddressToCustomerUseCase;
import com.eatz.application.customerAddresses.usecases.UpdateCustomerAddressUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.address.CustomerAddressDetails;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customerAddresses.CustomerAddress;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.presentation.web.address.mapper.AddressMapper;
import com.eatz.shared.dto.PasswordUpdateRequest;
import com.eatz.shared.exception.AddressNotFoundException;
import com.eatz.shared.usecases.UpdateUserPasswordUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CreateCustomerUseCase createCustomerUseCase;

    @Mock
    private SaveAddressUseCase saveAddressUseCase;

    @Mock
    private AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase;

    @Mock
    private GetCustomerUseCase getCustomerUseCase;

    @Mock
    private UpdateCustomerUseCase updateCustomerUseCase;

    @Mock
    private DeleteCustomerUseCase deleteCustomerUseCase;

    @Mock
    private UpdateUserPasswordUseCase<Customer> updatePasswordUseCase;

    @Mock
    private UpdateCustomerAddressUseCase updateCustomerAddressUseCase;

    @Mock
    private DeleteAddressUseCase deleteAddressUseCase;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private CustomerService customerService;

    @Nested
    @DisplayName("Create Customer")
    class CreateCustomer {

        @Test
        @DisplayName("Creates customer successfully without addresses")
        void createsCustomerSuccessfullyWithoutAddresses() {
            Customer customer = new Customer();
            customer.setId(1L);

            when(createCustomerUseCase.execute(customer)).thenReturn(customer);

            Customer result = customerService.createCustomer(customer, null);

            assertEquals(1L, result.getId());
            assertNull(result.getAddresses());
        }

        @Test
        @DisplayName("Creates customer successfully with addresses")
        void createsCustomerSuccessfullyWithAddresses() {
            Customer customer = new Customer();
            customer.setId(1L);
            customer.setAddresses(List.of(getCustomerAddressDetails()));

            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setStreet("Street");
            addressRequest.setNickname("Home");
            addressRequest.setDefaultAddress(true);

            Address savedAddress = new Address();
            savedAddress.setId(1L);
            savedAddress.setStreet("Street");

            when(createCustomerUseCase.execute(customer)).thenReturn(customer);
            when(saveAddressUseCase.execute(any(Address.class))).thenReturn(savedAddress);
            when(associateAddressToCustomerUseCase.execute(1L, savedAddress, "Home", true))
                    .thenReturn(new CustomerAddress(1L, 1L, savedAddress.getId(), "Home", true, false));

            Customer result = customerService.createCustomer(customer, addressRequest);

            assertEquals(1L, result.getId());
            assertNotNull(result.getAddresses());
            assertEquals(1, result.getAddresses().size());
            assertEquals(savedAddress.getId(), result.getAddresses().getFirst().getId());
            assertEquals("Home", result.getAddresses().getFirst().getNickname());
            assertTrue(result.getAddresses().getFirst().isDefault());
        }

        @Test
        @DisplayName("Throws exception when customer is null")
        void throwsExceptionWhenCustomerIsNull() {
            AddressRequest addressRequest = new AddressRequest();

            when(createCustomerUseCase.execute(null)).thenThrow(new IllegalArgumentException("Customer cannot be null."));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.createCustomer(null, addressRequest));

            assertEquals("Customer cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Does not throw exception when address request is null but customer has addresses")
        void doesNotThrowExceptionWhenAddressRequestIsNullButCustomerHasAddresses() {
            Customer customer = new Customer();
            customer.setAddresses(List.of(getCustomerAddressDetails()));

            when(createCustomerUseCase.execute(customer)).thenReturn(customer);

            assertDoesNotThrow(() -> customerService.createCustomer(customer, null));
        }

    }

    @Nested
    @DisplayName("Get Customer")
    class GetCustomer {

        @Test
        @DisplayName("Returns customer successfully")
        void returnsCustomerSuccessfully() {
            Long customerId = 1L;
            Customer customer = new Customer();
            customer.setId(customerId);
            customer.setName("João Silva");

            when(getCustomerUseCase.execute(customerId)).thenReturn(customer);

            Customer result = customerService.getCustomer(customerId);

            assertEquals(customerId, result.getId());
            assertEquals("João Silva", result.getName());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long customerId = 1L;

            when(getCustomerUseCase.execute(customerId)).thenThrow(new EntityNotFoundException("Customer not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.getCustomer(customerId));

            assertEquals("Customer not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update Customer")
    class UpdateCustomer {

        @Test
        @DisplayName("Updates customer successfully")
        void updatesCustomerSuccessfully() {
            Long customerId = 1L;
            Customer newData = new Customer();
            newData.setName("Updated Name");

            Customer updatedCustomer = new Customer();
            updatedCustomer.setId(customerId);
            updatedCustomer.setName("Updated Name");

            when(updateCustomerUseCase.execute(customerId, newData)).thenReturn(updatedCustomer);

            Customer result = customerService.updateCustomer(customerId, newData);

            assertEquals(customerId, result.getId());
            assertEquals("Updated Name", result.getName());
        }

        @Test
        @DisplayName("Throws exception when customer ID is null")
        void throwsExceptionWhenCustomerIdIsNull() {
            Customer newData = new Customer();

            when(updateCustomerUseCase.execute(null, newData)).thenThrow(new IllegalArgumentException("Customer ID cannot be null."));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.updateCustomer(null, newData));

            assertEquals("Customer ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when new data is null")
        void throwsExceptionWhenNewDataIsNull() {
            Long customerId = 1L;

            when(updateCustomerUseCase.execute(customerId, null)).thenThrow(new IllegalArgumentException("New customer data cannot be null."));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.updateCustomer(customerId, null));

            assertEquals("New customer data cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long customerId = 1L;
            Customer newData = new Customer();

            when(updateCustomerUseCase.execute(customerId, newData)).thenThrow(new EntityNotFoundException("Customer not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.updateCustomer(customerId, newData));

            assertEquals("Customer not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Delete Customer")
    class DeleteCustomer {

        @Test
        @DisplayName("Deletes customer successfully")
        void deletesCustomerSuccessfully() {
            Long customerId = 1L;

            assertDoesNotThrow(() -> customerService.deleteCustomer(customerId));
        }

        @Test
        @DisplayName("Throws exception when customer ID is null")
        void throwsExceptionWhenCustomerIdIsNull() {
            doThrow(new IllegalArgumentException("ID não pode ser nulo."))
                    .when(deleteCustomerUseCase).execute(null);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.deleteCustomer(null));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long customerId = 1L;

            doThrow(new EntityNotFoundException("Customer not found."))
                    .when(deleteCustomerUseCase).execute(customerId);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.deleteCustomer(customerId));

            assertEquals("Customer not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update Password")
    class UpdatePassword {

        @Test
        @DisplayName("Updates password successfully")
        void updatesPasswordSuccessfully() {
            String token = "validToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword", "newPassword");
            Customer customer = new Customer();
            customer.setEmail("test@example.com");
            customer.setPassword("oldPassword");

            when(jwtUtil.extractUsername(token)).thenReturn("test@example.com");
            when(getCustomerUseCase.execute("test@example.com")).thenReturn(customer);

            assertDoesNotThrow(() -> customerService.updatePassword(token, request));
            verify(updatePasswordUseCase, times(1)).execute(customer, "oldPassword", "newPassword");
        }

        @Test
        @DisplayName("Throws exception when token is invalid")
        void throwsExceptionWhenTokenIsInvalid() {
            String token = "invalidToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword", "newPassword");

            when(jwtUtil.extractUsername(token)).thenThrow(new IllegalArgumentException("Invalid token."));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.updatePassword(token, request));

            assertEquals("Invalid token.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            String token = "validToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword", "newPassword");

            when(jwtUtil.extractUsername(token)).thenReturn("test@example.com");
            when(getCustomerUseCase.execute("test@example.com")).thenThrow(new EntityNotFoundException("Customer not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.updatePassword(token, request));

            assertEquals("Customer not found.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when old password is incorrect")
        void throwsExceptionWhenOldPasswordIsIncorrect() {
            String token = "validToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("wrongPassword", "newPassword");
            Customer customer = new Customer();
            customer.setEmail("test@example.com");

            when(jwtUtil.extractUsername(token)).thenReturn("test@example.com");
            when(getCustomerUseCase.execute("test@example.com")).thenReturn(customer);
            doThrow(new IllegalArgumentException("Old password is incorrect."))
                    .when(updatePasswordUseCase).execute(customer, "wrongPassword", "newPassword");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.updatePassword(token, request));

            assertEquals("Old password is incorrect.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Get Customer By Username")
    class GetCustomerByUsername {

        @Test
        @DisplayName("Returns customer successfully when token is valid")
        void returnsCustomerSuccessfullyWhenTokenIsValid() {
            String token = "validToken";
            String email = "test@example.com";
            Customer customer = new Customer();
            customer.setEmail(email);

            when(jwtUtil.extractUsername(token)).thenReturn(email);
            when(getCustomerUseCase.execute(email)).thenReturn(customer);

            Customer result = customerService.getCustomerByUsername(token);

            assertEquals(email, result.getEmail());
        }

        @Test
        @DisplayName("Throws exception when token is invalid")
        void throwsExceptionWhenTokenIsInvalid() {
            String token = "invalidToken";

            when(jwtUtil.extractUsername(token)).thenThrow(new IllegalArgumentException("Invalid token."));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.getCustomerByUsername(token));

            assertEquals("Invalid token.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            String token = "validToken";
            String email = "test@example.com";

            when(jwtUtil.extractUsername(token)).thenReturn(email);
            when(getCustomerUseCase.execute(email)).thenThrow(new EntityNotFoundException("Customer not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.getCustomerByUsername(token));

            assertEquals("Customer not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Add Address")
    class AddAddress {

        @Test
        @DisplayName("Adds address successfully")
        void addsAddressSuccessfully() {
            Long customerId = 1L;
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setStreet("Street");
            addressRequest.setNumber("123");
            addressRequest.setNickname("Home");
            addressRequest.setDefaultAddress(true);

            Address address = new Address();
            address.setStreet(addressRequest.getStreet());
            address.setNumber(addressRequest.getNumber());

            Customer customer = new Customer();
            customer.setId(customerId);

            Address savedAddress = new Address();
            savedAddress.setId(1L);
            savedAddress.setStreet("Street");

            when(getCustomerUseCase.execute(customerId)).thenReturn(customer);
            when(addressMapper.toDomain(addressRequest)).thenReturn(address);
            when(saveAddressUseCase.execute(address)).thenReturn(savedAddress);

            assertDoesNotThrow(() -> customerService.addAddress(customerId, addressRequest));
            verify(associateAddressToCustomerUseCase, times(1))
                    .execute(customerId, savedAddress, "Home", true);
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long customerId = 1L;
            AddressRequest addressRequest = new AddressRequest();

            when(getCustomerUseCase.execute(customerId)).thenThrow(new EntityNotFoundException("Customer not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.addAddress(customerId, addressRequest));

            assertEquals("Customer not found.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when address request is null")
        void throwsExceptionWhenAddressRequestIsNull() {
            Long customerId = 1L;

            when(getCustomerUseCase.execute(customerId)).thenReturn(new Customer());
            when(addressMapper.toDomain(null)).thenThrow(new IllegalArgumentException("Address request cannot be null."));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.addAddress(customerId, null));

            assertEquals("Address request cannot be null.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update Address")
    class UpdateAddress {

        @Test
        @DisplayName("Updates address successfully")
        void updatesAddressSuccessfully() {
            Long customerId = 1L;
            Long addressId = 2L;
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setStreet("Updated Street");
            addressRequest.setNumber("456");

            Customer customer = new Customer();
            customer.setId(customerId);

            when(getCustomerUseCase.execute(customerId)).thenReturn(customer);

            assertDoesNotThrow(() -> customerService.updateAddress(customerId, addressId, addressRequest));
            verify(updateCustomerAddressUseCase, times(1)).execute(customer, addressId, addressRequest);
        }

        @Test
        @DisplayName("Throws exception when address ID is null")
        void throwsExceptionWhenAddressIdIsNull() {
            Long customerId = 1L;
            AddressRequest addressRequest = new AddressRequest();
            Customer customer = new Customer();
            customer.setId(customerId);

            when(getCustomerUseCase.execute(customerId)).thenReturn(customer);
            doThrow(new IllegalArgumentException("Address ID cannot be null."))
                    .when(updateCustomerAddressUseCase).execute(customer, null, addressRequest);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.updateAddress(customerId, null, addressRequest));

            assertEquals("Address ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when address request is null")
        void throwsExceptionWhenAddressRequestIsNull() {
            Long customerId = 1L;
            Long addressId = 2L;
            Customer customer = new Customer();
            customer.setId(customerId);

            when(getCustomerUseCase.execute(customerId)).thenReturn(customer);
            doThrow(new IllegalArgumentException("Address request cannot be null."))
                    .when(updateCustomerAddressUseCase).execute(customer, addressId, null);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerService.updateAddress(customerId, addressId, null));

            assertEquals("Address request cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long customerId = 1L;
            Long addressId = 2L;
            AddressRequest addressRequest = new AddressRequest();

            when(getCustomerUseCase.execute(customerId)).thenThrow(new EntityNotFoundException("Customer not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.updateAddress(customerId, addressId, addressRequest));

            assertEquals("Customer not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Delete Address")
    class DeleteAddressTests {

        @Test
        @DisplayName("Deletes address successfully")
        void deletesAddressSuccessfully() {
            Long customerId = 1L;
            Long addressId = 1L;

            Customer customer = new Customer();
            customer.setId(customerId);
            CustomerAddressDetails addressDetails = getCustomerAddressDetails();
            customer.setAddresses(List.of(addressDetails));

            when(getCustomerUseCase.execute(customerId)).thenReturn(customer);

            assertDoesNotThrow(() -> customerService.deleteAddress(customerId, addressId));
            verify(deleteAddressUseCase, times(1)).execute(addressDetails);
        }

        @Test
        @DisplayName("Throws exception when address is not found")
        void throwsExceptionWhenAddressIsNotFound() {
            Long customerId = 1L;
            Long addressId = 2L;

            Customer customer = new Customer();
            customer.setId(customerId);
            customer.setAddresses(List.of());

            when(getCustomerUseCase.execute(customerId)).thenReturn(customer);

            AddressNotFoundException exception = assertThrows(AddressNotFoundException.class,
                    () -> customerService.deleteAddress(customerId, addressId));

            assertNotNull(exception);
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long customerId = 1L;
            Long addressId = 2L;

            when(getCustomerUseCase.execute(customerId)).thenThrow(new EntityNotFoundException("Customer not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerService.deleteAddress(customerId, addressId));

            assertEquals("Customer not found.", exception.getMessage());
        }

    }

    private static CustomerAddressDetails getCustomerAddressDetails() {
        CustomerAddressDetails customerAddressDetails = new CustomerAddressDetails();
        customerAddressDetails.setId(1L);
        customerAddressDetails.setNickname("Home");
        customerAddressDetails.setDefault(true);
        return customerAddressDetails;
    }

}
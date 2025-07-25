package com.eatz.presentation.web.customer;

import com.eatz.application.customer.services.CustomerService;
import com.eatz.application.customer.usecases.AuthenticateCustomerUseCase;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.presentation.web.customer.dto.CustomerResponse;
import com.eatz.presentation.web.customer.dto.NewCustomerRequest;
import com.eatz.presentation.web.customer.dto.UpdateCustomerRequest;
import com.eatz.presentation.web.customer.mapper.CustomerMapper;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import com.eatz.shared.exception.InvalidPasswordException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private AuthenticateCustomerUseCase authenticateCustomerUseCase;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerController customerController;

    @Nested
    @DisplayName("Find Customer By Username")
    class FindCustomerByUsername {

        @Test
        @DisplayName("Returns customer successfully when token is valid")
        void returnsCustomerSuccessfullyWhenTokenIsValid() {
            String token = "validToken";
            String email = "customer@example.com";
            Customer customer = new Customer();
            customer.setEmail(email);
            CustomerResponse response = new CustomerResponse();
            response.setEmail(email);

            when(customerService.getCustomerByUsername(token)).thenReturn(customer);
            when(mapper.toResponse(customer)).thenReturn(response);

            ResponseEntity<CustomerResponse> result = customerController.findByUsername("Bearer " + token);

            assertNotNull(result.getBody());
            assertEquals(email, result.getBody().getEmail());
            assertEquals(HttpStatus.OK, result.getStatusCode());
        }

        @Test
        @DisplayName("Throws exception when token is invalid")
        void throwsExceptionWhenTokenIsInvalid() {
            String token = "invalidToken";

            when(customerService.getCustomerByUsername(token)).thenThrow(new RuntimeException("Invalid token"));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> customerController.findByUsername("Bearer " + token));

            assertEquals("Invalid token", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            String token = "validToken";

            when(customerService.getCustomerByUsername(token)).thenThrow(new EntityNotFoundException("Customer not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerController.findByUsername("Bearer " + token));

            assertEquals("Customer not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Find Customer By ID")
    class FindCustomerById {

        @Test
        @DisplayName("Returns customer successfully when ID is valid")
        void returnsCustomerSuccessfullyWhenIdIsValid() {
            Long id = 1L;
            Customer customer = new Customer();
            customer.setId(id);
            customer.setEmail("customer@example.com");
            CustomerResponse response = new CustomerResponse();
            response.setEmail("customer@example.com");

            when(customerService.getCustomer(id)).thenReturn(customer);
            when(mapper.toResponse(customer)).thenReturn(response);

            ResponseEntity<CustomerResponse> result = customerController.findById(id);

            assertNotNull(result.getBody());
            assertEquals("customer@example.com", result.getBody().getEmail());
            assertEquals(HttpStatus.OK, result.getStatusCode());
        }

        @Test
        @DisplayName("Throws exception when ID is invalid")
        void throwsExceptionWhenIdIsInvalid() {
            Long id = -1L;

            when(customerService.getCustomer(id)).thenThrow(new IllegalArgumentException("Invalid ID"));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerController.findById(id));

            assertEquals("Invalid ID", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long id = 999L;

            when(customerService.getCustomer(id)).thenThrow(new EntityNotFoundException("Customer not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerController.findById(id));

            assertEquals("Customer not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Create Customer")
    class CreateCustomer {

        @Test
        @DisplayName("Creates customer successfully with valid request")
        void createsCustomerSuccessfullyWithValidRequest() {
            NewCustomerRequest request = new NewCustomerRequest();
            AddressRequest addressRequest = new AddressRequest();
            request.setAddress(addressRequest);
            Customer customer = new Customer();
            customer.setId(1L);
            CustomerResponse response = new CustomerResponse();
            response.setEmail("customer@example.com");

            when(mapper.toDomain(request)).thenReturn(customer);
            when(customerService.createCustomer(customer, addressRequest)).thenReturn(customer);
            when(mapper.toResponse(customer)).thenReturn(response);

            ResponseEntity<CustomerResponse> result = customerController.create(request);

            assertNotNull(result.getBody());
            assertEquals("customer@example.com", result.getBody().getEmail());
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertNotNull(result.getHeaders().getLocation());
            assertEquals("/customers/1", result.getHeaders().getLocation().toString());
        }

        @Test
        @DisplayName("Throws exception when customer creation fails")
        void throwsExceptionWhenCustomerCreationFails() {
            NewCustomerRequest request = new NewCustomerRequest();
            AddressRequest addressRequest = new AddressRequest();
            request.setAddress(addressRequest);
            Customer customer = new Customer();

            when(mapper.toDomain(request)).thenReturn(customer);
            when(customerService.createCustomer(customer, addressRequest))
                    .thenThrow(new RuntimeException("Customer creation failed"));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> customerController.create(request));

            assertEquals("Customer creation failed", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Add Address")
    class AddAddress {

        @Test
        @DisplayName("Adds address successfully when request is valid")
        void addsAddressSuccessfullyWhenRequestIsValid() {
            Long customerId = 1L;
            AddressRequest addressRequest = new AddressRequest();
            doNothing().when(customerService).addAddress(customerId, addressRequest);

            ResponseEntity<Void> result = customerController.addAddress(customerId, addressRequest);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(customerService, times(1)).addAddress(customerId, addressRequest);
        }

        @Test
        @DisplayName("Throws exception when is a non-existent customer")
        void throwsExceptionWhenNonExistentCustomer() {
            Long customerId = 1L;
            AddressRequest addressRequest = new AddressRequest();

            doThrow(new CustomerNotFoundException("Usuário não encontrado com o id: " + customerId))
                    .when(customerService).addAddress(customerId, addressRequest);

            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                    () -> customerController.addAddress(customerId, addressRequest));

            assertEquals("Usuário não encontrado com o id: " + customerId, exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when address request is invalid")
        void throwsExceptionWhenAddressRequestIsInvalid() {
            Long customerId = 1L;
            AddressRequest addressRequest = null;
            doThrow(new IllegalArgumentException("Endereço não pode ser nulo."))
                    .when(customerService).addAddress(customerId, addressRequest);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerController.addAddress(customerId, addressRequest));

            assertEquals("Endereço não pode ser nulo.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Login Customer")
    class LoginCustomer {

        @Test
        @DisplayName("Returns authentication response successfully when login request is valid")
        void returnsAuthenticationResponseSuccessfullyWhenLoginRequestIsValid() {
            LoginRequest request = new LoginRequest("customer@email.com", "password123");
            AuthenticationResponse authResponse = new AuthenticationResponse(
                    "token123",
                    "Bearer",
                    new Date(),
                    "customer@email.com"
            );

            when(authenticateCustomerUseCase.execute(request)).thenReturn(authResponse);

            ResponseEntity<AuthenticationResponse> result = customerController.login(request);

            assertNotNull(result.getBody());
            assertEquals("token123", result.getBody().token());
            assertEquals(HttpStatus.OK, result.getStatusCode());
        }

        @Test
        @DisplayName("Throws exception when authentication fails")
        void throwsExceptionWhenAuthenticationFails() {
            LoginRequest request = new LoginRequest("customer@email.com", "wrongpassword");

            when(authenticateCustomerUseCase.execute(request))
                    .thenThrow(new RuntimeException("Authentication failed"));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> customerController.login(request));

            assertEquals("Authentication failed", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update Customer")
    class UpdateCustomer {

        @Test
        @DisplayName("Updates customer successfully when request is valid")
        void updatesCustomerSuccessfullyWhenRequestIsValid() {
            Long id = 1L;
            UpdateCustomerRequest request = new UpdateCustomerRequest();
            Customer customer = new Customer();
            customer.setId(id);
            customer.setEmail("updated@example.com");
            CustomerResponse response = new CustomerResponse();
            response.setEmail("updated@example.com");

            when(mapper.toDomain(request)).thenReturn(customer);
            when(customerService.updateCustomer(id, customer)).thenReturn(customer);
            when(mapper.toResponse(customer)).thenReturn(response);

            ResponseEntity<CustomerResponse> result = customerController.update(id, request);

            assertNotNull(result.getBody());
            assertEquals("updated@example.com", result.getBody().getEmail());
            assertEquals(HttpStatus.OK, result.getStatusCode());
        }

        @Test
        @DisplayName("Throws exception when customer ID is invalid")
        void throwsExceptionWhenCustomerIdIsInvalid() {
            Long id = -1L;
            UpdateCustomerRequest request = new UpdateCustomerRequest();
            Customer customer = new Customer();

            when(mapper.toDomain(request)).thenReturn(customer);
            when(customerService.updateCustomer(id, customer)).thenThrow(new IllegalArgumentException("Invalid ID"));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerController.update(id, request));

            assertEquals("Invalid ID", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long id = 999L;
            UpdateCustomerRequest request = new UpdateCustomerRequest();
            Customer customer = new Customer();

            when(mapper.toDomain(request)).thenReturn(customer);
            when(customerService.updateCustomer(id, customer)).thenThrow(new EntityNotFoundException("Customer not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerController.update(id, request));

            assertEquals("Customer not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update Address")
    class UpdateAddress {

        @Test
        @DisplayName("Updates address successfully when request is valid")
        void updatesAddressSuccessfullyWhenRequestIsValid() {
            Long customerId = 1L;
            Long addressId = 1L;
            AddressRequest addressRequest = new AddressRequest();

            ResponseEntity<Void> result = customerController.updateAddress(customerId, addressId, addressRequest);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(customerService, times(1)).updateAddress(customerId, addressId, addressRequest);
        }

        @Test
        @DisplayName("Throws exception when customer ID is invalid")
        void throwsExceptionWhenCustomerIdIsInvalid() {
            Long customerId = -1L;
            Long addressId = 1L;
            AddressRequest addressRequest = new AddressRequest();

            doThrow(new IllegalArgumentException("ID não pode ser nulo."))
                    .when(customerService).updateAddress(customerId, addressId, addressRequest);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> customerController.updateAddress(customerId, addressId, addressRequest));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer or address is not found")
        void throwsExceptionWhenCustomerOrAddressIsNotFound() {
            Long customerId = 1L;
            Long addressId = 999L;
            AddressRequest addressRequest = new AddressRequest();

            doThrow(new EntityNotFoundException("Customer or address not found"))
                    .when(customerService).updateAddress(customerId, addressId, addressRequest);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerController.updateAddress(customerId, addressId, addressRequest));

            assertEquals("Customer or address not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update Password")
    class UpdatePassword {

        @Test
        @DisplayName("Updates password successfully when request is valid")
        void updatesPasswordSuccessfullyWhenRequestIsValid() {
            String token = "validToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword123", "newPassword123");

            doNothing().when(customerService).updatePassword(token, request);

            ResponseEntity<Void> result = customerController.updatePassword("Bearer " + token, request);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(customerService, times(1)).updatePassword(token, request);
        }

        @Test
        @DisplayName("Throws exception when password is invalid")
        void throwsExceptionWhenTokenIsInvalid() {
            String token = "token";
            PasswordUpdateRequest request = new PasswordUpdateRequest("wrongPassword", "newPassword123");

            doThrow(new InvalidPasswordException())
                    .when(customerService).updatePassword(token, request);

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> customerController.updatePassword("Bearer " + token, request));

            assertEquals("Password is invalid.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Delete Customer")
    class DeleteCustomer {

        @Test
        @DisplayName("Deletes customer successfully when ID is valid")
        void deletesCustomerSuccessfullyWhenIdIsValid() {
            Long id = 1L;

            doNothing().when(customerService).deleteCustomer(id);

            ResponseEntity<Void> result = customerController.delete(id);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(customerService, times(1)).deleteCustomer(id);
        }

        @Test
        @DisplayName("Throws exception when customer is not found")
        void throwsExceptionWhenCustomerIsNotFound() {
            Long id = 999L;

            doThrow(new EntityNotFoundException("Customer not found"))
                    .when(customerService).deleteCustomer(id);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerController.delete(id));

            assertEquals("Customer not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Delete Address")
    class DeleteAddress {

        @Test
        @DisplayName("Deletes address successfully")
        void deletesAddressSuccessfully() {
            Long customerId = 1L;
            Long addressId = 1L;

            doNothing().when(customerService).deleteAddress(customerId, addressId);

            ResponseEntity<Void> result = customerController.deleteAddress(customerId, addressId);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(customerService, times(1)).deleteAddress(customerId, addressId);
        }

        @Test
        @DisplayName("Throws exception when customer or address is not found")
        void throwsExceptionWhenCustomerOrAddressIsNotFound() {
            Long customerId = 1L;
            Long addressId = 999L;

            doThrow(new EntityNotFoundException("Customer or address not found"))
                    .when(customerService).deleteAddress(customerId, addressId);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> customerController.deleteAddress(customerId, addressId));

            assertEquals("Customer or address not found", exception.getMessage());
        }

    }

}
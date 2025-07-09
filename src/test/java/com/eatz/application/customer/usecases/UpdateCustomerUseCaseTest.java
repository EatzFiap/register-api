package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UpdateCustomerUseCase updateCustomerUseCase;

    @Nested
    @DisplayName("Execute Update Customer")
    class ExecuteUpdateCustomer {

        @Test
        @DisplayName("Updates customer successfully with all fields")
        void updatesCustomerSuccessfullyWithAllFields() {
            Long id = 1L;
            Customer existingCustomer = new Customer();
            existingCustomer.setId(id);
            existingCustomer.setName("Old Name");
            existingCustomer.setEmail("old@email.com");

            Customer newData = new Customer();
            newData.setName("Updated Name");
            newData.setEmail("updated@email.com");
            newData.setPhone("123456789");
            newData.setCpf("12345678900");
            newData.setProfileImageUrl("http://image.url");

            when(customerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(existingCustomer));
            when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

            Customer result = updateCustomerUseCase.execute(id, newData);

            assertEquals("Updated Name", result.getName());
            assertEquals("updated@email.com", result.getEmail());
            assertEquals("123456789", result.getPhone());
            assertEquals("12345678900", result.getCpf());
            assertEquals("http://image.url", result.getProfileImageUrl());
            assertNotNull(result.getUpdatedAt());
        }

        @Test
        @DisplayName("Updates customer successfully with partial fields")
        void updatesCustomerSuccessfullyWithPartialFields() {
            Long id = 1L;
            Customer existingCustomer = new Customer();
            existingCustomer.setId(id);
            existingCustomer.setName("Old Name");
            existingCustomer.setEmail("old@email.com");

            Customer newData = new Customer();
            newData.setName("Updated Name");

            when(customerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(existingCustomer));
            when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

            Customer result = updateCustomerUseCase.execute(id, newData);

            assertEquals("Updated Name", result.getName());
            assertEquals("old@email.com", result.getEmail());
            assertNotNull(result.getUpdatedAt());
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            Customer customer = new Customer();
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> updateCustomerUseCase.execute(null, customer));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when new data is null")
        void throwsExceptionWhenNewDataIsNull() {
            Long id = 1L;

            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> updateCustomerUseCase.execute(id, null));

            assertEquals("Dados para atualização não podem ser nulos.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer does not exist")
        void throwsExceptionWhenCustomerDoesNotExist() {
            Long id = 1L;
            Customer newData = new Customer();
            newData.setName("Updated Name");

            when(customerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            CustomerNotFoundException exception =
                    assertThrows(CustomerNotFoundException.class, () -> updateCustomerUseCase.execute(id, newData));

            assertEquals("Usuário não encontrado para atualização.", exception.getMessage());
        }

    }

}
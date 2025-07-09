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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private GetCustomerUseCase getCustomerUseCase;

    @Nested
    @DisplayName("Execute by ID")
    class ExecuteById {

        @Test
        @DisplayName("Returns customer when ID exists")
        void returnsCustomerWhenIdExists() {
            Long id = 1L;
            Customer customer = new Customer();
            customer.setId(id);
            customer.setEmail("example@email.com");
            customer.setName("João Silva");
            customer.setCreatedAt(LocalDateTime.now());

            when(customerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(customer));

            Customer result = getCustomerUseCase.execute(id);

            assertEquals(customer, result);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> getCustomerUseCase.execute((Long) null));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer does not exist")
        void throwsExceptionWhenCustomerDoesNotExist() {
            Long id = 1L;

            when(customerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            CustomerNotFoundException exception =
                    assertThrows(CustomerNotFoundException.class, () -> getCustomerUseCase.execute(id));

            assertEquals("Usuário não encontrado com o id: " + id, exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Execute by Email")
    class ExecuteByEmail {

        @Test
        @DisplayName("Returns customer when email exists")
        void returnsCustomerWhenEmailExists() {
            String email = "example@email.com";
            Customer customer = new Customer();
            customer.setEmail(email);
            customer.setName("João Silva");

            when(customerRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.of(customer));

            Customer result = getCustomerUseCase.execute(email);

            assertEquals(customer, result);
        }

        @Test
        @DisplayName("Throws exception when email is null")
        void throwsExceptionWhenEmailIsNull() {
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> getCustomerUseCase.execute((String) null));

            assertEquals("Email cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer does not exist for email")
        void throwsExceptionWhenCustomerDoesNotExistForEmail() {
            String email = "nonexistent@email.com";

            when(customerRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.empty());

            CustomerNotFoundException exception =
                    assertThrows(CustomerNotFoundException.class, () -> getCustomerUseCase.execute(email));

            assertEquals("User not found with email: " + email, exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Execute without parameters")
    class ExecuteWithoutParameters {

        @Test
        @DisplayName("Returns all customers when repository is not empty")
        void returnsAllCustomersWhenRepositoryIsNotEmpty() {
            Customer customer1 = new Customer();
            customer1.setId(1L);
            customer1.setName("João Silva");

            Customer customer2 = new Customer();
            customer2.setId(2L);
            customer2.setName("Maria Oliveira");

            List<Customer> customers = List.of(customer1, customer2);

            when(customerRepository.findAll()).thenReturn(customers);

            List<Customer> result = getCustomerUseCase.execute();

            assertEquals(customers, result);
        }

        @Test
        @DisplayName("Throws exception when repository is empty")
        void throwsExceptionWhenRepositoryIsEmpty() {
            when(customerRepository.findAll()).thenReturn(List.of());

            CustomerNotFoundException exception =
                    assertThrows(CustomerNotFoundException.class, () -> getCustomerUseCase.execute());

            assertEquals("Nenhum usuário encontrado.", exception.getMessage());
        }

    }

}
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DeleteCustomerUseCase deleteCustomerUseCase;

    @Nested
    class Execute {

        @Test
        @DisplayName("Deletes customer successfully when ID is valid")
        void deletesCustomerSuccessfully() {
            Long id = 1L;
            Customer customer = new Customer();
            customer.setId(id);
            customer.setDeleted(false);

            when(customerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(customer));

            deleteCustomerUseCase.execute(id);

            assertEquals(id, customer.getId());
            assertTrue(customer.isDeleted());
            assertNotNull(customer.getUpdatedAt());
            verify(customerRepository).save(customer);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> deleteCustomerUseCase.execute(null));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when customer does not exist")
        void throwsExceptionWhenCustomerDoesNotExist() {
            Long id = 1L;

            when(customerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            CustomerNotFoundException exception =
                    assertThrows(CustomerNotFoundException.class, () -> deleteCustomerUseCase.execute(id));

            assertEquals("Usuário não encontrado para exclusão.", exception.getMessage());
        }

    }

}
package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.customer.exceptions.CustomerAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;

    @Nested
    class Execute {

        @Test
        @DisplayName("Should create a customer successfully")
        void createsCustomerSuccessfully() {
            Customer customer = new Customer();
            customer.setEmail("new@example.com");
            customer.setPassword("password");
            customer.setName("João Silva");
            customer.setPhone("123456789");
            customer.setCpf("12345678901");
            customer.setProfileImageUrl("imageUrl");

            when(customerRepository.existsByEmailAndIsDeletedFalse(customer.getEmail())).thenReturn(false);
            when(passwordEncoder.encode(customer.getPassword())).thenReturn("encodedPassword");
            when(customerRepository.save(customer)).thenReturn(customer);

            Customer result = createCustomerUseCase.execute(customer);

            assertNotNull(result.getCreatedAt());
            assertEquals(customer.getEmail(), result.getEmail());
            assertEquals(customer.getName(), result.getName());
            assertEquals(customer.getPhone(), result.getPhone());
            assertFalse(result.isDeleted());
            assertEquals("encodedPassword", result.getPassword());

            verify(customerRepository).save(customer);
        }

        @Test
        @DisplayName("Should throw exception when customer is null")
        void throwsExceptionWhenCustomerIsNull() {
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> createCustomerUseCase.execute(null));
            assertEquals("Usuário não pode ser nulo.", exception.getMessage());
        }

        @ParameterizedTest
        @DisplayName("Should throw exception when email is null or blank")
        @CsvSource({
                "null, E-mail é obrigatório.",
                "' ', E-mail é obrigatório."
        })
        void throwsExceptionWhenEmailIsInvalid(String email, String expectedMessage) {
            Customer customer = new Customer();
            customer.setEmail(email.equals("null") ? null : email);

            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> createCustomerUseCase.execute(customer));

            assertEquals(expectedMessage, exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when customer already exists")
        void throwsExceptionWhenCustomerAlreadyExists() {
            Customer customer = new Customer();
            customer.setEmail("existing@example.com");

            when(customerRepository.existsByEmailAndIsDeletedFalse(customer.getEmail())).thenReturn(true);

            CustomerAlreadyExistsException exception =
                    assertThrows(CustomerAlreadyExistsException.class, () -> createCustomerUseCase.execute(customer));

            assertEquals("Já existe um usuário com este e-mail.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when password is null")
        void throwsExceptionWhenPasswordIsNull() {
            Customer customer = new Customer();
            customer.setEmail("new@example.com");
            customer.setPassword(null);

            NullPointerException exception =
                    assertThrows(NullPointerException.class, () -> createCustomerUseCase.execute(customer));

            assertEquals("Senha é obrigatória.", exception.getMessage());
        }

    }

}
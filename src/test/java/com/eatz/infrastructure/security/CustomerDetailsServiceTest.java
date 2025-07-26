package com.eatz.infrastructure.security;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerDetailsServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerDetailsService customerDetailsService;

    @Nested
    @DisplayName("Load Customer By Username")
    class LoadCustomerByUsername {

        @Test
        void loadsCustomerSuccessfullyWhenUsernameExists() {
            String username = "customer@example.com";
            Customer customer = new Customer();
            customer.setEmail(username);
            customer.setPassword("password123");

            when(customerRepository.findByEmailAndIsDeletedFalse(username)).thenReturn(Optional.of(customer));

            UserDetails userDetails = customerDetailsService.loadUserByUsername(username);

            assertNotNull(userDetails);
            assertEquals(username, userDetails.getUsername());
            assertEquals("password123", userDetails.getPassword());
            assertThat(userDetails.getAuthorities())
                    .extracting("authority")
                    .containsExactly("CUSTOMER");
        }

        @Test
        void throwsExceptionWhenCustomerDoesNotExist() {
            String username = "nonexistent@example.com";

            when(customerRepository.findByEmailAndIsDeletedFalse(username)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerDetailsService.loadUserByUsername(username))
                    .isInstanceOf(JwtException.class)
                    .hasMessageContaining("User not found: " + username);
        }

        @Test
        void throwsExceptionWhenUsernameIsNull() {
            assertThatThrownBy(() -> customerDetailsService.loadUserByUsername(null))
                    .isInstanceOf(JwtException.class)
                    .hasMessageContaining("User not found: null");
        }

    }

}
package com.eatz.infrastructure.security;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositeUserDetailsServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    @InjectMocks
    private CompositeUserDetailsService compositeUserDetailsService;

    @Nested
    @DisplayName("Load User By Username")
    class LoadUserByUsername {

        @Test
        void loadsCustomerSuccessfullyWhenUsernameExists() {
            String username = "customer@example.com";
            Customer customer = new Customer();
            customer.setEmail(username);
            customer.setPassword("password123");

            when(customerRepository.findByEmailAndIsDeletedFalse(username)).thenReturn(Optional.of(customer));

            UserDetails userDetails = compositeUserDetailsService.loadUserByUsername(username);

            assertNotNull(userDetails);
            assertThat(userDetails.getUsername()).isEqualTo(username);
            assertThat(userDetails.getPassword()).isEqualTo("password123");
            assertThat(userDetails.getAuthorities()).extracting("authority").containsExactly("CUSTOMER");
        }

        @Test
        void loadsRestaurantUserSuccessfullyWhenUsernameExists() {
            String username = "restaurant@example.com";
            RestaurantUser restaurantUser = new RestaurantUser();
            restaurantUser.setEmail(username);
            restaurantUser.setPassword("securePassword");

            when(customerRepository.findByEmailAndIsDeletedFalse(username)).thenReturn(Optional.empty());
            when(restaurantUserRepository.findByEmailAndIsDeletedFalse(username)).thenReturn(Optional.of(restaurantUser));

            UserDetails userDetails = compositeUserDetailsService.loadUserByUsername(username);

            assertThat(userDetails.getUsername()).isEqualTo(username);
            assertThat(userDetails.getPassword()).isEqualTo("securePassword");
            assertThat(userDetails.getAuthorities()).extracting("authority").containsExactly("ADMIN");
        }

        @Test
        void throwsExceptionWhenUserDoesNotExist() {
            String username = "nonexistent@example.com";

            when(customerRepository.findByEmailAndIsDeletedFalse(username)).thenReturn(Optional.empty());
            when(restaurantUserRepository.findByEmailAndIsDeletedFalse(username)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> compositeUserDetailsService.loadUserByUsername(username))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessageContaining("User not found: " + username);
        }

        @Test
        void throwsExceptionWhenUsernameIsNull() {
            assertThatThrownBy(() -> compositeUserDetailsService.loadUserByUsername(null))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessageContaining("User not found: null");
        }

    }

}
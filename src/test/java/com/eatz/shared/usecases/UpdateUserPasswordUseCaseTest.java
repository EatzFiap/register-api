package com.eatz.shared.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.shared.domain.User;
import com.eatz.shared.domain.UserRepository;
import com.eatz.shared.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserPasswordUseCaseTest {

    @Mock
    private UserRepository<User> repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdateUserPasswordUseCase<User> updateUserPasswordUseCase;

    @Nested
    @DisplayName("Update User Password")
    class ExecuteUpdateUserPassword {

        @Test
        @DisplayName("Updates restaurant user password successfully")
        void updatesRestaurantUserPasswordSuccessfully() {
            User restaurantUser = new RestaurantUser();
            restaurantUser.setPassword("encodedOldPassword");

            when(passwordEncoder.matches("correctOldPassword", restaurantUser.getPassword())).thenReturn(true);
            when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
            when(repository.save(restaurantUser)).thenReturn(restaurantUser);

            updateUserPasswordUseCase.execute(restaurantUser, "correctOldPassword", "newPassword");

            assertEquals("encodedNewPassword", restaurantUser.getPassword());
        }

        @Test
        @DisplayName("Updates customer password successfully")
        void updatesCustomerPasswordSuccessfully() {
            User customerUser = new Customer();
            customerUser.setPassword("encodedOldPassword");

            when(passwordEncoder.matches("correctOldPassword", customerUser.getPassword())).thenReturn(true);
            when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
            when(repository.save(customerUser)).thenReturn(customerUser);

            updateUserPasswordUseCase.execute(customerUser, "correctOldPassword", "newPassword");

            assertEquals("encodedNewPassword", customerUser.getPassword());
        }

        @Test
        @DisplayName("Throws exception when old password does not match")
        void throwsExceptionWhenOldPasswordDoesNotMatch() {
            User user = new RestaurantUser();
            user.setPassword("encodedOldPassword");

            when(passwordEncoder.matches("wrongOldPassword", user.getPassword())).thenReturn(false);

            InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                    () -> updateUserPasswordUseCase.execute(user, "wrongOldPassword", "newPassword"));

            assertNotNull(exception);
            assertEquals("Password is invalid.", exception.getMessage());
        }

    }

}
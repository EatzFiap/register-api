package com.eatz.application.restaurantUser.services;

import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.application.restaurantUser.usecases.*;
import com.eatz.domain.address.Address;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import com.eatz.shared.usecases.UpdateUserPasswordUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static com.eatz.helper.RestaurantUserHelper.createRestaurantUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUserServiceTest {

    @Mock
    private CreateRestaurantUserUseCase createUserUseCase;

    @Mock
    private SaveAddressUseCase saveAddressUseCase;

    @Mock
    private GetRestaurantUserUseCase getUserUseCase;

    @Mock
    private UpdateRestaurantUserUseCase updateUserUseCase;

    @Mock
    private DeleteRestaurantUserUseCase deleteUserUseCase;

    @Mock
    private AuthenticateRestaurantUserUseCase authenticateRestaurantUserUseCase;

    @Mock
    private UpdateUserPasswordUseCase<RestaurantUser> updatePasswordUseCase;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private RestaurantUserService restaurantUserService;

    @Nested
    @DisplayName("Create User")
    class CreateUser {

        @Test
        @DisplayName("Creates user successfully")
        void createsUserSuccessfully() {
            RestaurantUser restaurantUser = new RestaurantUser();
            Address address = new Address();
            address.setStreet("Street");
            restaurantUser.setAddress(address);

            Address savedAddress = new Address();
            savedAddress.setStreet("Street");
            savedAddress.setId(1L);

            RestaurantUser savedUser = new RestaurantUser();
            savedUser.setId(1L);
            savedUser.setAddress(savedAddress);

            when(saveAddressUseCase.execute(address)).thenReturn(savedAddress);
            when(createUserUseCase.execute(restaurantUser)).thenReturn(savedUser);

            RestaurantUser result = restaurantUserService.createUser(restaurantUser);

            assertEquals(savedUser.getId(), result.getId());
            assertEquals(savedAddress.getId(), result.getAddress().getId());
        }

        @Test
        @DisplayName("Throws exception when address is null")
        void throwsExceptionWhenAddressIsNull() {
            RestaurantUser restaurantUser = new RestaurantUser();
            restaurantUser.setAddress(null);

            when(saveAddressUseCase.execute(null)).thenThrow(new IllegalArgumentException("Address cannot be null."));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> restaurantUserService.createUser(restaurantUser));

            assertEquals("Address cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user creation fails")
        void throwsExceptionWhenUserCreationFails() {
            RestaurantUser restaurantUser = new RestaurantUser();
            Address address = new Address();
            address.setStreet("Street");
            restaurantUser.setAddress(address);

            Address savedAddress = new Address();
            savedAddress.setStreet("Street");
            savedAddress.setId(1L);

            when(saveAddressUseCase.execute(address)).thenReturn(savedAddress);
            when(createUserUseCase.execute(restaurantUser)).thenThrow(new RuntimeException("User creation failed."));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> restaurantUserService.createUser(restaurantUser));

            assertEquals("User creation failed.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Find User By ID")
    class FindUserById {

        @Test
        @DisplayName("Returns user successfully when ID is valid")
        void returnsUserSuccessfullyWhenIdIsValid() {
            Long userId = 1L;
            RestaurantUser user = new RestaurantUser();
            user.setId(userId);

            when(getUserUseCase.execute(userId)).thenReturn(user);

            RestaurantUser result = restaurantUserService.findById(userId);

            assertEquals(userId, result.getId());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long userId = 1L;

            when(getUserUseCase.execute(userId)).thenThrow(new EntityNotFoundException("User not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserService.findById(userId));

            assertEquals("User not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update User")
    class UpdateUser {

        @Test
        @DisplayName("Updates user successfully with new address")
        void updatesUserSuccessfullyWithNewAddress() {
            Long userId = 1L;

            RestaurantUser existingUser = new RestaurantUser();
            existingUser.setId(userId);
            Address existingAddress = new Address();
            existingAddress.setId(2L);
            existingAddress.setStreet("Old Street");
            existingAddress.setNumber("456");
            existingUser.setAddress(existingAddress);

            RestaurantUser newData = new RestaurantUser();
            newData.setId(userId);
            Address savedAddress = new Address();
            savedAddress.setId(2L);
            savedAddress.setStreet("Street");
            savedAddress.setNumber("123");
            newData.setAddress(savedAddress);

            when(getUserUseCase.execute(userId)).thenReturn(existingUser);
            when(saveAddressUseCase.execute(any(Address.class))).thenReturn(savedAddress);
            when(updateUserUseCase.execute(userId, newData)).thenReturn(newData);

            RestaurantUser result = restaurantUserService.updateUser(userId, newData);

            assertNotNull(result);
            assertEquals(userId, result.getId());
            assertEquals(savedAddress.getId(), result.getAddress().getId());
            assertEquals("Street", result.getAddress().getStreet());
            assertEquals("123", result.getAddress().getNumber());
        }

        @Test
        @DisplayName("Updates user successfully without changing address")
        void updatesUserSuccessfullyWithoutChangingAddress() {
            Long userId = 1L;
            RestaurantUser newData = new RestaurantUser();
            newData.setAddress(null);

            RestaurantUser existingUser = new RestaurantUser();
            existingUser.setId(userId);
            Address existingAddress = new Address();
            existingAddress.setId(2L);
            existingUser.setAddress(existingAddress);

            when(getUserUseCase.execute(userId)).thenReturn(existingUser);
            when(updateUserUseCase.execute(userId, newData)).thenReturn(newData);

            RestaurantUser result = restaurantUserService.updateUser(userId, newData);

            assertNull(result.getAddress());
        }

        @Test
        @DisplayName("Updates user successfully when existing user's address is null")
        void updatesUserSuccessfullyWhenExistingUsersAddressIsNotNull() {
            Long userId = 1L;
            RestaurantUser existingUser = createRestaurantUser();
            existingUser.setId(userId);
            existingUser.setAddress(null);

            RestaurantUser newData = createRestaurantUser();
            newData.setProfileImageUrl("newImageUrl.png");
            Address newAddress = new Address();
            newAddress.setStreet("New Street");
            newData.setAddress(newAddress);

            when(getUserUseCase.execute(userId)).thenReturn(existingUser);
            when(saveAddressUseCase.execute(newAddress)).thenReturn(newAddress);
            when(updateUserUseCase.execute(userId, newData)).thenReturn(newData);

            RestaurantUser result = restaurantUserService.updateUser(userId, newData);

            assertNotNull(result);
            assertEquals(existingUser.getName(), result.getName());
            assertEquals("New Street", result.getAddress().getStreet());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long userId = 1L;
            RestaurantUser newData = new RestaurantUser();

            when(getUserUseCase.execute(userId)).thenThrow(new EntityNotFoundException("User not found."));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserService.updateUser(userId, newData));

            assertEquals("User not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Delete User")
    class DeleteUser {

        @Test
        @DisplayName("Deletes user successfully when ID is valid")
        void deletesUserSuccessfullyWhenIdIsValid() {
            Long userId = 1L;

            assertDoesNotThrow(() -> restaurantUserService.deleteUser(userId));
            verify(deleteUserUseCase, times(1)).execute(userId);
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            doThrow(new IllegalArgumentException("ID cannot be null."))
                    .when(deleteUserUseCase).execute(null);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> restaurantUserService.deleteUser(null));

            assertEquals("ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long userId = 1L;

            doThrow(new EntityNotFoundException("User not found."))
                    .when(deleteUserUseCase).execute(userId);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserService.deleteUser(userId));

            assertEquals("User not found.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Authenticate User")
    class AuthenticateUserTests {

        @Test
        @DisplayName("Authenticates user successfully with valid login request")
        void authenticatesUserSuccessfullyWithValidLoginRequest() {
            LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

            AuthenticationResponse expectedResponse =
                new AuthenticationResponse("token", "Bearer", new Date(), "user@example.com");

            when(authenticateRestaurantUserUseCase.execute(loginRequest)).thenReturn(expectedResponse);

            AuthenticationResponse result = restaurantUserService.authenticate(loginRequest);

            assertEquals(expectedResponse.token(), result.token());
            assertEquals(expectedResponse.type(), result.type());
            assertEquals(expectedResponse.expiresAt(), result.expiresAt());
            assertEquals(expectedResponse.username(), result.username());
        }

        @Test
        @DisplayName("Throws exception when authentication fails")
        void throwsExceptionWhenAuthenticationFails() {
            LoginRequest loginRequest = new LoginRequest("user@example.com", "wrongpassword");

            when(authenticateRestaurantUserUseCase.execute(loginRequest))
                    .thenThrow(new RuntimeException("Authentication failed."));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> restaurantUserService.authenticate(loginRequest));

            assertEquals("Authentication failed.", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Update Password")
    class UpdatePassword {

        @Test
        @DisplayName("Updates password successfully with valid token and request")
        void updatesPasswordSuccessfullyWithValidTokenAndRequest() {
            String token = "validToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword", "newPassword");
            String email = "user@example.com";
            RestaurantUser user = new RestaurantUser();
            user.setEmail(email);

            when(jwtUtil.extractUsername(token)).thenReturn(email);
            when(getUserUseCase.execute(email)).thenReturn(user);

            assertDoesNotThrow(() -> restaurantUserService.updatePassword(token, request));
            verify(updatePasswordUseCase, times(1)).execute(user, "oldPassword", "newPassword");
        }

        @Test
        @DisplayName("Throws exception when token is invalid")
        void throwsExceptionWhenTokenIsInvalid() {
            String token = "invalidToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword", "newPassword");

            when(jwtUtil.extractUsername(token)).thenThrow(new RuntimeException("Invalid token"));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> restaurantUserService.updatePassword(token, request));

            assertEquals("Invalid token", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            String token = "validToken";
            PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword", "newPassword");
            String email = "user@example.com";

            when(jwtUtil.extractUsername(token)).thenReturn(email);
            when(getUserUseCase.execute(email)).thenThrow(new EntityNotFoundException("User not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserService.updatePassword(token, request));

            assertEquals("User not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Get User By Username")
    class GetUserByUsername {

        @Test
        @DisplayName("Returns user successfully when token is valid")
        void returnsUserSuccessfullyWhenTokenIsValid() {
            String token = "validToken";
            String email = "user@example.com";
            RestaurantUser user = new RestaurantUser();
            user.setEmail(email);

            when(jwtUtil.extractUsername(token)).thenReturn(email);
            when(getUserUseCase.execute(email)).thenReturn(user);

            RestaurantUser result = restaurantUserService.getUserByUsername(token);

            assertEquals(email, result.getEmail());
        }

        @Test
        @DisplayName("Throws exception when token is invalid")
        void throwsExceptionWhenTokenIsInvalid() {
            String token = "invalidToken";

            when(jwtUtil.extractUsername(token)).thenThrow(new RuntimeException("Invalid token"));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> restaurantUserService.getUserByUsername(token));

            assertEquals("Invalid token", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            String token = "validToken";
            String email = "user@example.com";

            when(jwtUtil.extractUsername(token)).thenReturn(email);
            when(getUserUseCase.execute(email)).thenThrow(new EntityNotFoundException("User not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserService.getUserByUsername(token));

            assertEquals("User not found", exception.getMessage());
        }

    }

}
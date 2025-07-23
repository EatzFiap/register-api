package com.eatz.presentation.web.restaurantUser;

import com.eatz.application.restaurantUser.services.RestaurantUserService;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserRequest;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserResponse;
import com.eatz.presentation.web.restaurantUser.dto.UpdateRestaurantUserRequest;
import com.eatz.presentation.web.restaurantUser.mapper.RestaurantUserMapper;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUserControllerTest {

    @Mock
    private RestaurantUserService restaurantUserService;

    @Mock
    private RestaurantUserMapper mapper;

    @InjectMocks
    private RestaurantUserController restaurantUserController;

    @Nested
    @DisplayName("Find By Username")
    class FindByUsername {

        @Test
        @DisplayName("Returns user successfully when token is valid")
        void returnsUserSuccessfullyWhenTokenIsValid() {
            String token = "validToken";
            RestaurantUser user = new RestaurantUser();
            user.setEmail("user@email.com");
            RestaurantUserResponse response = new RestaurantUserResponse();
            response.setEmail("user@email.com");

            when(restaurantUserService.getUserByUsername(token)).thenReturn(user);
            when(mapper.toResponse(user)).thenReturn(response);

            ResponseEntity<RestaurantUserResponse> result = restaurantUserController.findByUsername("Bearer " + token);

            assertNotNull(result.getBody());
            assertEquals("user@email.com", result.getBody().getEmail());
            assertEquals(HttpStatus.OK, result.getStatusCode());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            String token = "validToken";

            when(restaurantUserService.getUserByUsername(token)).thenThrow(new EntityNotFoundException("User not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserController.findByUsername("Bearer " + token));

            assertEquals("User not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Find By ID")
    class FindById {

        @Test
        @DisplayName("Returns user successfully when ID is valid")
        void returnsUserSuccessfullyWhenIdIsValid() {
            Long id = 1L;
            RestaurantUser user = new RestaurantUser();
            user.setId(id);
            user.setEmail("user@email.com");
            RestaurantUserResponse response = new RestaurantUserResponse();
            response.setEmail("user@email.com");

            when(restaurantUserService.findById(id)).thenReturn(user);
            when(mapper.toResponse(user)).thenReturn(response);

            ResponseEntity<RestaurantUserResponse> result = restaurantUserController.findById(id);

            assertNotNull(result.getBody());
            assertEquals("user@email.com", result.getBody().getEmail());
            assertEquals(HttpStatus.OK, result.getStatusCode());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long id = 999L;

            when(restaurantUserService.findById(id)).thenThrow(new EntityNotFoundException("User not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserController.findById(id));

            assertEquals("User not found", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Create Restaurant User")
    class CreateRestaurantUser {

        @Test
        @DisplayName("Creates user successfully when request is valid")
        void createsUserSuccessfullyWhenRequestIsValid() {
            String newUserEmail = "newUser@email.com";

            RestaurantUserRequest request = new RestaurantUserRequest();
            request.setEmail(newUserEmail);
            RestaurantUser user = new RestaurantUser();
            user.setEmail(newUserEmail);
            user.setId(1L);
            RestaurantUserResponse response = new RestaurantUserResponse();
            response.setEmail(newUserEmail);

            when(mapper.toDomain(request)).thenReturn(user);
            when(restaurantUserService.createUser(user)).thenReturn(user);
            when(mapper.toResponse(user)).thenReturn(response);

            ResponseEntity<RestaurantUserResponse> result = restaurantUserController.create(request);

            assertNotNull(result.getBody());
            assertEquals(newUserEmail, result.getBody().getEmail());
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertNotNull(result.getHeaders().getLocation());
            assertEquals("/customers/1", result.getHeaders().getLocation().toString());
        }

        @Test
        @DisplayName("Throws exception when user creation fails")
        void throwsExceptionWhenUserCreationFails() {
            String newUserEmail = "newUser@email.com";

            RestaurantUserRequest request = new RestaurantUserRequest();
            request.setEmail(newUserEmail);
            RestaurantUser user = new RestaurantUser();
            user.setEmail(newUserEmail);

            when(mapper.toDomain(request)).thenReturn(user);
            when(restaurantUserService.createUser(user)).thenThrow(new RuntimeException("User creation failed"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> restaurantUserController.create(request));

            assertEquals("User creation failed", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("Login")
    class Login {

        @Test
        @DisplayName("Returns authentication response successfully when login request is valid")
        void returnsAuthenticationResponseSuccessfullyWhenLoginRequestIsValid() {
            LoginRequest request = getLoginRequest();
            AuthenticationResponse authResponse = new AuthenticationResponse(
                    "validToken",
                    "Bearer",
                    new Date(),
                    "restaurantUser@email.com"
            );

            when(restaurantUserService.authenticate(request)).thenReturn(authResponse);

            ResponseEntity<AuthenticationResponse> result = restaurantUserController.login(request);

            assertThat(result)
                    .isNotNull()
                    .isEqualTo(new ResponseEntity<>(authResponse, HttpStatus.OK));

            assertNotNull(result.getBody());
            assertEquals("validToken", result.getBody().token());
        }

        @Test
        @DisplayName("Throws exception when login request is invalid")
        void throwsExceptionWhenLoginRequestIsInvalid() {
            LoginRequest request = getLoginRequest();

            when(restaurantUserService.authenticate(request)).thenThrow(new RuntimeException("Invalid credentials"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> restaurantUserController.login(request));

            assertThat(exception)
                    .isNotNull()
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Invalid credentials");
        }

    }

    @Nested
    @DisplayName("Update Restaurant User")
    class UpdateRestaurantUser {

        @Test
        @DisplayName("Updates user successfully when request is valid")
        void updatesUserSuccessfullyWhenRequestIsValid() {
            Long id = 1L;
            UpdateRestaurantUserRequest request = new UpdateRestaurantUserRequest();
            request.setEmail("updatedUser@email.com");
            RestaurantUser user = new RestaurantUser();
            user.setEmail(request.getEmail());
            RestaurantUser updatedUser = new RestaurantUser();
            updatedUser.setEmail(request.getEmail());
            RestaurantUserResponse response = new RestaurantUserResponse();
            response.setEmail(request.getEmail());

            when(mapper.toDomain(request)).thenReturn(user);
            when(restaurantUserService.updateUser(id, user)).thenReturn(updatedUser);
            when(mapper.toResponse(updatedUser)).thenReturn(response);

            ResponseEntity<RestaurantUserResponse> result = restaurantUserController.update(id, request);

            assertThat(result)
                    .isNotNull();
            assertNotNull(result.getBody());
            assertThat(result.getBody().getEmail())
                    .isEqualTo(request.getEmail());
            assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long id = 999L;
            UpdateRestaurantUserRequest request = new UpdateRestaurantUserRequest();
            request.setEmail("nonexistentUser@email.com");
            RestaurantUser user = new RestaurantUser();
            user.setEmail(request.getEmail());

            when(mapper.toDomain(request)).thenReturn(user);
            when(restaurantUserService.updateUser(id, user)).thenThrow(new EntityNotFoundException("User not found"));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserController.update(id, request));

            assertThat(exception)
                    .isNotNull()
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("User not found");
        }

    }

    @Nested
    @DisplayName("Update Password")
    class UpdatePassword {

        @Test
        @DisplayName("Updates password successfully when request is valid")
        void updatesPasswordSuccessfullyWhenRequestIsValid() {
            String token = "validToken";
            PasswordUpdateRequest request = getPasswordUpdateRequest();

            doNothing().when(restaurantUserService).updatePassword(token, request);

            ResponseEntity<Void> result = restaurantUserController.updatePassword("Bearer " + token, request);

            assertThat(result).isNotNull();
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        @DisplayName("Throws exception when customer not found")
        void throwsExceptionWhenTokenIsInvalid() {
            String token = "invalidToken";
            PasswordUpdateRequest request = getPasswordUpdateRequest();

            doThrow(new CustomerNotFoundException("User not found with email: restaurantUser.email.com"))
                    .when(restaurantUserService).updatePassword(token, request);

            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                    () -> restaurantUserController.updatePassword("Bearer " + token, request));

            assertThat(exception)
                    .isNotNull()
                    .isInstanceOf(CustomerNotFoundException.class)
                    .hasMessage("User not found with email: restaurantUser.email.com");
        }

        @Test
        @DisplayName("Throws exception when request is invalid")
        void throwsExceptionWhenRequestIsInvalid() {
            String token = "validToken";
            PasswordUpdateRequest request = getPasswordUpdateRequest();

            doThrow(new IllegalArgumentException("Invalid request"))
                    .when(restaurantUserService).updatePassword(token, request);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> restaurantUserController.updatePassword("Bearer " + token, request));

            assertThat(exception)
                    .isNotNull()
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid request");
        }

    }

    @Nested
    @DisplayName("Delete Restaurant User")
    class DeleteRestaurantUserTests {

        @Test
        @DisplayName("Deletes user successfully when ID is valid")
        void deletesUserSuccessfullyWhenIdIsValid() {
            Long id = 1L;

            doNothing().when(restaurantUserService).deleteUser(id);

            ResponseEntity<Void> result = restaurantUserController.delete(id);

            assertThat(result).isNotNull();
            assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long id = 999L;

            doThrow(new EntityNotFoundException("User not found")).when(restaurantUserService).deleteUser(id);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> restaurantUserController.delete(id));

            assertThat(exception)
                    .isNotNull()
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("User not found");
        }

    }

    private static @NotNull PasswordUpdateRequest getPasswordUpdateRequest() {
        return new PasswordUpdateRequest(
                "oldPassword",
                "newPassword"
        );
    }

    private static @NotNull LoginRequest getLoginRequest() {
        return new LoginRequest("restaurantUser@email.com", "password");
    }

}
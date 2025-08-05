package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.domain.restaurantUser.exceptions.RestaurantNotFoundException;
import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUserUseCaseTest {

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    @Mock
    private RestaurantUserTypeRepository restaurantUserTypeRepository;

    @InjectMocks
    private UpdateRestaurantUserUseCase updateRestaurantUserUseCase;

    @Nested
    @DisplayName("Update Restaurant User")
    class ExecuteUpdateRestaurantUser {

        @Test
        @DisplayName("Updates restaurant user successfully with null fields")
        void updatesRestaurantUserSuccessfully_WithNullFields() {
            Long id = 1L;
            RestaurantUser existingUser = new RestaurantUser();
            existingUser.setId(id);
            existingUser.setName("Old Name");
            existingUser.setEmail("old@example.com");

            RestaurantUser newData = new RestaurantUser();
            newData.setProfileImageUrl("newImageUrl.jpg");

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(existingUser));
            when(restaurantUserRepository.save(any(RestaurantUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

            RestaurantUser result = updateRestaurantUserUseCase.execute(id, newData);

            assertEquals("Old Name", result.getName());
            assertEquals("newImageUrl.jpg", result.getProfileImageUrl());
            assertNotNull(result.getUpdatedAt());
        }

        @Test
        @DisplayName("Updates restaurant user successfully")
        void updatesRestaurantUserSuccessfully_WithAllFields() {
            Long id = 1L;
            Address address = new Address();

            RestaurantUser existingUser = new RestaurantUser();
            existingUser.setId(id);
            existingUser.setName("Existing Name");
            existingUser.setEmail("existingUser@example.com");
            existingUser.setPhone("123456789");
            existingUser.setCpf("123.456.789-00");
            existingUser.setRole(RestaurantRole.EMPLOYEE);
            existingUser.setAddress(address);

            RestaurantUser newData = new RestaurantUser();
            newData.setName("Updated Name");
            newData.setEmail("existingUser@example.com");
            newData.setPhone("987654321");
            newData.setCpf("123.456.789-01");
            newData.setRole(RestaurantRole.MANAGER);
            newData.setAddress(address);

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(existingUser));
            when(restaurantUserRepository.save(any(RestaurantUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

            RestaurantUser result = updateRestaurantUserUseCase.execute(id, newData);

            assertEquals("Updated Name", result.getName());
            assertEquals("existingUser@example.com", result.getEmail());
            assertEquals("987654321", result.getPhone());
            assertEquals("123.456.789-01", result.getCpf());
            assertEquals(RestaurantRole.MANAGER, result.getRole());
            assertEquals(address, result.getAddress());
        }

        @Test
        @DisplayName("Throws exception when ID is null")
        void throwsExceptionWhenIdIsNull() {
            RestaurantUser newData = new RestaurantUser();
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> updateRestaurantUserUseCase.execute(null, newData));

            assertEquals("ID não pode ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when new data is null")
        void throwsExceptionWhenNewDataIsNull() {
            Long id = 1L;
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> updateRestaurantUserUseCase.execute(id, null));

            assertEquals("Dados para atualização não podem ser nulos.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when user is not found")
        void throwsExceptionWhenUserIsNotFound() {
            Long id = 1L;
            RestaurantUser newData = new RestaurantUser();

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                    () -> updateRestaurantUserUseCase.execute(id, newData));

            assertEquals("Usuário não encontrado para atualização.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when email is already in use by another user")
        void throwsExceptionWhenEmailIsAlreadyInUseByAnotherUser() {
            Long id = 1L;
            RestaurantUser existingUser = new RestaurantUser();
            existingUser.setId(id);
            existingUser.setEmail("existing@example.com");

            RestaurantUser newData = new RestaurantUser();
            newData.setEmail("new@example.com");

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(existingUser));
            when(restaurantUserRepository.existsByEmailAndIsDeletedFalse("new@example.com")).thenReturn(true);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> updateRestaurantUserUseCase.execute(id, newData));

            assertEquals("Este e-mail já está em uso.", exception.getMessage());
        }

        @Test
        @DisplayName("Throws exception when restaurant user type ID does not exist")
        void throwsExceptionWhenRestaurantUserTypeIdDoesNotExist() {
            Long id = 1L;
            RestaurantUser existingUser = new RestaurantUser();
            existingUser.setId(id);

            RestaurantUser newData = new RestaurantUser();
            newData.setRestaurantUserTypeId(2L);

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(existingUser));
            when(restaurantUserTypeRepository.findById(2L)).thenReturn(Optional.empty());

            RestaurantUserTypeNotFoundException exception = assertThrows(RestaurantUserTypeNotFoundException.class,
                    () -> updateRestaurantUserUseCase.execute(id, newData));

            assertEquals("Restaurant user type not found with id: 2", exception.getMessage());
        }

        @Test
        @DisplayName("Updates restaurant user type successfully when valid type ID is provided")
        void updatesRestaurantUserTypeSuccessfullyWhenValidTypeIdIsProvided() {
            Long id = 1L;
            RestaurantUser existingUser = new RestaurantUser();
            existingUser.setId(id);

            RestaurantUser newData = new RestaurantUser();
            newData.setRestaurantUserTypeId(2L);

            RestaurantUserType restaurantUserType = new RestaurantUserType();
            restaurantUserType.setId(2L);
            restaurantUserType.setName("MANAGER");

            when(restaurantUserRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(existingUser));
            when(restaurantUserTypeRepository.findById(2L)).thenReturn(Optional.of(restaurantUserType));
            when(restaurantUserRepository.save(any(RestaurantUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

            RestaurantUser result = updateRestaurantUserUseCase.execute(id, newData);

            assertEquals(2L, result.getRestaurantUserTypeId());
            verify(restaurantUserTypeRepository).findById(2L);
            verify(restaurantUserRepository).save(existingUser);
        }

    }

}
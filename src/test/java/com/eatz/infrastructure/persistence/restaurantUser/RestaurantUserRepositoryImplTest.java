package com.eatz.infrastructure.persistence.restaurantUser;

import com.eatz.domain.restaurantUser.RestaurantUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.eatz.helper.RestaurantUserHelper.createRestaurantUser;
import static com.eatz.helper.RestaurantUserHelper.createRestaurantUserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUserRepositoryImplTest {

    @Mock
    private JpaRestaurantUserRepository jpaRepository;

    @InjectMocks
    private RestaurantUserRepositoryImpl restaurantUserRepository;

    @Nested
    @DisplayName("Save RestaurantUser")
    class SaveRestaurantUser {

        @Test
        @DisplayName("Saves RestaurantUser successfully")
        void savesRestaurantUserSuccessfully() {
            RestaurantUser user = createRestaurantUser();
            RestaurantUserEntity entity = createRestaurantUserEntity();

            when(jpaRepository.save(any(RestaurantUserEntity.class))).thenReturn(entity);

            RestaurantUser result = restaurantUserRepository.save(user);

            assertNotNull(result);
            assertThat(result)
                    .usingRecursiveComparison()
                    .ignoringFields("id", "password", "address", "createdAt", "updatedAt")
                    .isEqualTo(user);
            verify(jpaRepository, times(1)).save(any(RestaurantUserEntity.class));
        }

    }

    @Nested
    @DisplayName("Find Restaurant User by email")
    class FindRestaurantUserByEmailAndIsDeletedFalse {

        @Test
        @DisplayName("Finds RestaurantUser by email successfully when not deleted")
        void findsRestaurantUserByEmailSuccessfullyWhenNotDeleted() {
            String email = "maria.oliveira@email.com";
            RestaurantUserEntity entity = createRestaurantUserEntity();
            RestaurantUser user = createRestaurantUser();

            when(jpaRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.of(entity));

            Optional<RestaurantUser> result = restaurantUserRepository.findByEmailAndIsDeletedFalse(email);

            assertTrue(result.isPresent());
            assertThat(result.get())
                    .usingRecursiveComparison()
                    .ignoringFields("id", "password", "address", "createdAt", "updatedAt")
                    .isEqualTo(user);
            verify(jpaRepository, times(1)).findByEmailAndIsDeletedFalse(email);
        }

        @Test
        @DisplayName("Returns empty when Restaurant User is not found by email or is deleted")
        void returnsEmptyWhenRestaurantUserIsNotFoundByEmailOrIsDeleted() {
            String email = "user@example.com";

            when(jpaRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.empty());

            Optional<RestaurantUser> result = restaurantUserRepository.findByEmailAndIsDeletedFalse(email);

            assertTrue(result.isEmpty());
            verify(jpaRepository, times(1)).findByEmailAndIsDeletedFalse(email);
        }

    }

    @Nested
    @DisplayName("Find Restaurant User by email")
    class FindRestaurantUserByEmail {

        @Test
        @DisplayName("Finds RestaurantUser by email successfully")
        void findsRestaurantUserByEmailSuccessfully() {
            String email = "maria.oliveira@email.com";
            RestaurantUserEntity entity = createRestaurantUserEntity();
            RestaurantUser user = createRestaurantUser();

            when(jpaRepository.findByEmail(email)).thenReturn(Optional.of(entity));

            Optional<RestaurantUser> result = restaurantUserRepository.findByEmail(email);

            assertTrue(result.isPresent());
            assertThat(result.get())
                    .usingRecursiveComparison()
                    .ignoringFields("id", "password", "address", "createdAt", "updatedAt")
                    .isEqualTo(user);
            verify(jpaRepository, times(1)).findByEmail(email);
        }

        @Test
        @DisplayName("Returns empty when RestaurantUser is not found by email")
        void returnsEmptyWhenRestaurantUserIsNotFoundByEmail() {
            String email = "nonexistentuser@example.com";

            when(jpaRepository.findByEmail(email)).thenReturn(Optional.empty());

            Optional<RestaurantUser> result = restaurantUserRepository.findByEmail(email);

            assertTrue(result.isEmpty());
            verify(jpaRepository, times(1)).findByEmail(email);
        }

    }

    @Nested
    @DisplayName("Delete Restaurant User")
    class DeleteRestaurantUser {

        @Test
        @DisplayName("Deletes RestaurantUser successfully")
        void deletesRestaurantUserSuccessfully() {
            RestaurantUser user = new RestaurantUser();

            restaurantUserRepository.delete(user);

            verify(jpaRepository, times(1)).delete(any(RestaurantUserEntity.class));
        }

    }

    @Nested
    @DisplayName("Check existence of RestaurantUser by email and not deleted")
    class ExistsByEmailAndIsDeletedFalse {

        @Test
        @DisplayName("Returns true when RestaurantUser exists by email and is not deleted")
        void returnsTrueWhenRestaurantUserExistsByEmailAndIsNotDeleted() {
            String email = "user@example.com";

            when(jpaRepository.existsByEmailAndIsDeletedFalse(email)).thenReturn(true);

            boolean result = restaurantUserRepository.existsByEmailAndIsDeletedFalse(email);

            assertTrue(result);
            verify(jpaRepository, times(1)).existsByEmailAndIsDeletedFalse(email);
        }

        @Test
        @DisplayName("Returns false when RestaurantUser does not exist by email or is deleted")
        void returnsFalseWhenRestaurantUserDoesNotExistByEmailOrIsDeleted() {
            String email = "nonexistentuser@example.com";

            when(jpaRepository.existsByEmailAndIsDeletedFalse(email)).thenReturn(false);

            boolean result = restaurantUserRepository.existsByEmailAndIsDeletedFalse(email);

            assertFalse(result);
            verify(jpaRepository, times(1)).existsByEmailAndIsDeletedFalse(email);
        }

    }

    @Nested
    @DisplayName("Find Restaurant User by ID and Is Deleted is False")
    class FindByIdAndIsDeletedFalse {

        @Test
        @DisplayName("Finds RestaurantUser by ID successfully when not deleted")
        void findsRestaurantUserByIdSuccessfullyWhenNotDeleted() {
            Long id = 1L;
            RestaurantUserEntity entity = createRestaurantUserEntity();
            RestaurantUser expectedResult = createRestaurantUser();

            when(jpaRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(entity));

            Optional<RestaurantUser> result = restaurantUserRepository.findByIdAndIsDeletedFalse(id);

            assertTrue(result.isPresent());
            assertThat(result.get())
                    .usingRecursiveComparison()
                    .ignoringFields("id", "password", "address", "createdAt", "updatedAt")
                    .isEqualTo(expectedResult);
            verify(jpaRepository, times(1)).findByIdAndIsDeletedFalse(id);
        }


        @Test
        @DisplayName("Returns empty when RestaurantUser is not found by ID or is deleted")
        void returnsEmptyWhenRestaurantUserIsNotFoundByIdOrIsDeleted() {
            Long id = 1L;

            when(jpaRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            Optional<RestaurantUser> result = restaurantUserRepository.findByIdAndIsDeletedFalse(id);

            assertTrue(result.isEmpty());
            verify(jpaRepository, times(1)).findByIdAndIsDeletedFalse(id);
        }

    }

    @Nested
    @DisplayName("Find all Restaurant Users")
    class FindAllRestaurantUsers {

        @Test
        @DisplayName("Finds all Restaurant Users successfully when none are deleted")
        void findsAllRestaurantUsersSuccessfullyWhenNoneAreDeleted() {
            List<RestaurantUserEntity> entities = List.of(createRestaurantUserEntity(), createRestaurantUserEntity());
            List<RestaurantUser> users = List.of(createRestaurantUser(), createRestaurantUser());

            when(jpaRepository.findAllByIsDeletedFalse()).thenReturn(entities);

            List<RestaurantUser> result = restaurantUserRepository.findAll();

            assertNotNull(result);
            assertEquals(users.size(), result.size());
            assertThat(result)
                    .usingRecursiveComparison()
                    .ignoringFields("id", "password", "address", "createdAt", "updatedAt")
                    .isEqualTo(users);
            verify(jpaRepository, times(1)).findAllByIsDeletedFalse();
        }

        @Test
        @DisplayName("Returns empty list when no Restaurant Users are found")
        void returnsEmptyListWhenNoRestaurantUsersAreFound() {
            when(jpaRepository.findAllByIsDeletedFalse()).thenReturn(List.of());

            List<RestaurantUser> result = restaurantUserRepository.findAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(jpaRepository, times(1)).findAllByIsDeletedFalse();
        }

    }

    @Nested
    @DisplayName("Find Role by Email and Is Deleted is False")
    class findRoleByEmailAndIsDeletedFalse {

        @Test
        @DisplayName("Finds role by email successfully when not deleted")
        void findsRoleByEmailSuccessfullyWhenNotDeleted() {
            String email = "user@example.com";
            String role = "ADMIN";

            when(jpaRepository.findRoleByEmailAndIsDeletedFalse(email)).thenReturn(Optional.of(role));

            Optional<String> result = restaurantUserRepository.findRoleByEmailAndIsDeletedFalse(email);

            assertTrue(result.isPresent());
            assertEquals(role, result.get());
            verify(jpaRepository, times(1)).findRoleByEmailAndIsDeletedFalse(email);
        }

        @Test
        @DisplayName("Returns empty when role is not found by email or user is deleted")
        void returnsEmptyWhenRoleIsNotFoundByEmailOrUserIsDeleted() {
            String email = "user@example.com";

            when(jpaRepository.findRoleByEmailAndIsDeletedFalse(email)).thenReturn(Optional.empty());

            Optional<String> result = restaurantUserRepository.findRoleByEmailAndIsDeletedFalse(email);

            assertTrue(result.isEmpty());
            verify(jpaRepository).findRoleByEmailAndIsDeletedFalse(email);
        }

    }

}
package com.eatz.infrastructure.persistence.restaurantUser;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.infrastructure.persistence.address.AddressEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.eatz.helper.RestaurantUserHelper.createRestaurantUser;
import static com.eatz.helper.RestaurantUserHelper.createRestaurantUserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUserEntityMapperTest {

    @Nested
    @DisplayName("Converts RestaurantUser to RestaurantUserEntity")
    class ToEntity {

        @Test
        @DisplayName("Converts RestaurantUser to RestaurantUserEntity successfully when user is valid")
        void convertsRestaurantUserToRestaurantUserEntitySuccessfullyWhenUserIsValid() {
            RestaurantUser user = createRestaurantUser();

            RestaurantUserEntity entity = RestaurantUserEntityMapper.toEntity(user);

            assertNotNull(entity);
            assertThat(entity)
                    .extracting(
                            RestaurantUserEntity::getId,
                            RestaurantUserEntity::getRole,
                            RestaurantUserEntity::getName,
                            RestaurantUserEntity::getEmail,
                            RestaurantUserEntity::getPassword,
                            RestaurantUserEntity::getCpf,
                            RestaurantUserEntity::getPhone,
                            RestaurantUserEntity::getCreatedAt,
                            RestaurantUserEntity::getUpdatedAt,
                            RestaurantUserEntity::isDeleted,
                            RestaurantUserEntity::getProfileImageUrl,
                            RestaurantUserEntity::getRestaurantId
                    )
                    .containsExactly(
                            user.getId(),
                            user.getRole().name(),
                            user.getName(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getCpf(),
                            user.getPhone(),
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
                            user.isDeleted(),
                            user.getProfileImageUrl(),
                            user.getRestaurantId()
                    );

            assertNotNull(entity.getAddress());
        }

        @Test
        @DisplayName("Returns null when RestaurantUser is null")
        void returnsNullWhenRestaurantUserIsNull() {
            RestaurantUserEntity entity = RestaurantUserEntityMapper.toEntity(null);

            assertNull(entity);
        }

        @Test
        @DisplayName("Sets null role in RestaurantUserEntity when RestaurantUser role is null")
        void setsNullRoleInRestaurantUserEntityWhenRestaurantUserRoleIsNull() {
            RestaurantUser user = createRestaurantUser();
            user.setRole(null);

            RestaurantUserEntity entity = RestaurantUserEntityMapper.toEntity(user);

            assertNotNull(entity);
            assertNull(entity.getRole());
            assertEquals(user.getName(), entity.getName());
        }

    }

    @Nested
    @DisplayName("Converts RestaurantUserEntity to RestaurantUser")
    class ToDomain {

        @Test
        @DisplayName("Converts RestaurantUserEntity to RestaurantUser successfully when entity is valid")
        void convertsRestaurantUserEntityToRestaurantUserSuccessfullyWhenEntityIsValid() {
            RestaurantUserEntity entity = createRestaurantUserEntity();

            RestaurantUser user = RestaurantUserEntityMapper.toDomain(entity);

            assertNotNull(user);
            assertThat(user)
                    .extracting(
                            RestaurantUser::getId,
                            RestaurantUser::getRole,
                            RestaurantUser::getName,
                            RestaurantUser::getEmail,
                            RestaurantUser::getPassword,
                            RestaurantUser::getCpf,
                            RestaurantUser::getPhone,
                            RestaurantUser::getCreatedAt,
                            RestaurantUser::getUpdatedAt,
                            RestaurantUser::isDeleted,
                            RestaurantUser::getProfileImageUrl,
                            RestaurantUser::getRestaurantId
                    )
                    .containsExactly(
                            entity.getId(),
                            RestaurantRole.valueOf(entity.getRole()),
                            entity.getName(),
                            entity.getEmail(),
                            entity.getPassword(),
                            entity.getCpf(),
                            entity.getPhone(),
                            entity.getCreatedAt(),
                            entity.getUpdatedAt(),
                            entity.isDeleted(),
                            entity.getProfileImageUrl(),
                            entity.getRestaurantId()
                    );

            assertNotNull(user.getAddress());
        }

        @Test
        @DisplayName("Returns null when RestaurantUserEntity is null")
        void returnsNullWhenRestaurantUserEntityIsNull() {
            RestaurantUser user = RestaurantUserEntityMapper.toDomain(null);

            assertNull(user);
        }

        @Test
        @DisplayName("Sets null role in RestaurantUser when RestaurantUserEntity role is null")
        void setsNullRoleInRestaurantUserWhenRestaurantUserEntityRoleIsNull() {
            RestaurantUserEntity entity = createRestaurantUserEntity();
            entity.setRole(null);

            RestaurantUser user = RestaurantUserEntityMapper.toDomain(entity);

            assertNotNull(user);
            assertNull(user.getRole());
            assertEquals(entity.getName(), user.getName());
        }

        @Test
        @DisplayName("Ignores deleted address when converting RestaurantUserEntity to RestaurantUser")
        void ignoresDeletedAddressWhenConvertingRestaurantUserEntityToRestaurantUser() {
            AddressEntity deletedAddress = new AddressEntity();
            deletedAddress.setDeleted(true);

            RestaurantUserEntity entity = createRestaurantUserEntity();
            entity.setAddress(deletedAddress);

            RestaurantUser user = RestaurantUserEntityMapper.toDomain(entity);

            assertNotNull(user);
            assertThat(user.getEmail()).isEqualTo(entity.getEmail());
            assertNull(user.getAddress());
        }

        @Test
        @DisplayName("Ignores null address when converting RestaurantUserEntity to RestaurantUser")
        void ignoresNullAddressWhenConvertingRestaurantUserEntityToRestaurantUser() {
            RestaurantUserEntity entity = createRestaurantUserEntity();
            entity.setAddress(null);

            RestaurantUser user = RestaurantUserEntityMapper.toDomain(entity);

            assertNotNull(user);
            assertThat(user.getEmail()).isEqualTo(entity.getEmail());
            assertNull(user.getAddress());
        }

    }

}
package com.eatz.infrastructure.persistence.mapper;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.infrastructure.persistence.entity.RestaurantUserTypeEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantUserTypeEntityMapperTest {

    @Test
    void shouldConvertEntityToDomainSuccessfully() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        RestaurantUserTypeEntity entity = new RestaurantUserTypeEntity(
                1L, "ADMIN", "Administrador", now, now, false
        );

        // When
        RestaurantUserType domain = RestaurantUserTypeEntityMapper.toDomain(entity);

        // Then
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("ADMIN");
        assertThat(domain.getDescription()).isEqualTo("Administrador");
        assertThat(domain.getCreatedAt()).isEqualTo(now);
        assertThat(domain.getUpdatedAt()).isEqualTo(now);
        assertThat(domain.isDeleted()).isFalse();
    }

    @Test
    void shouldConvertDomainToEntitySuccessfully() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        RestaurantUserType domain = new RestaurantUserType(
                1L, "MANAGER", "Gerente", now, now, false
        );

        // When
        RestaurantUserTypeEntity entity = RestaurantUserTypeEntityMapper.toEntity(domain);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("MANAGER");
        assertThat(entity.getDescription()).isEqualTo("Gerente");
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getUpdatedAt()).isEqualTo(now);
        assertThat(entity.isDeleted()).isFalse();
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        // When
        RestaurantUserType domain = RestaurantUserTypeEntityMapper.toDomain(null);

        // Then
        assertThat(domain).isNull();
    }

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        // When
        RestaurantUserTypeEntity entity = RestaurantUserTypeEntityMapper.toEntity(null);

        // Then
        assertThat(entity).isNull();
    }
} 
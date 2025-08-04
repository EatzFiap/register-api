package com.eatz.presentation.web.restaurantUserType.mapper;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeRequest;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantUserTypeMapperTest {

    @Test
    void shouldConvertRequestToDomainSuccessfully() {
        // Given
        RestaurantUserTypeRequest request = new RestaurantUserTypeRequest("ADMIN", "Administrador do sistema");

        // When
        RestaurantUserType domain = RestaurantUserTypeMapper.toDomain(request);

        // Then
        assertThat(domain).isNotNull();
        assertThat(domain.getName()).isEqualTo("ADMIN");
        assertThat(domain.getDescription()).isEqualTo("Administrador do sistema");
    }

    @Test
    void shouldConvertDomainToResponseSuccessfully() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        RestaurantUserType domain = new RestaurantUserType(
                1L, "MANAGER", "Gerente do restaurante", now, now, false
        );

        // When
        RestaurantUserTypeResponse response = RestaurantUserTypeMapper.toResponse(domain);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("MANAGER");
        assertThat(response.getDescription()).isEqualTo("Gerente do restaurante");
        assertThat(response.getCreatedAt()).isEqualTo(now);
        assertThat(response.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldReturnNullWhenRequestIsNull() {
        // When
        RestaurantUserType domain = RestaurantUserTypeMapper.toDomain(null);

        // Then
        assertThat(domain).isNull();
    }

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        // When
        RestaurantUserTypeResponse response = RestaurantUserTypeMapper.toResponse(null);

        // Then
        assertThat(response).isNull();
    }
} 
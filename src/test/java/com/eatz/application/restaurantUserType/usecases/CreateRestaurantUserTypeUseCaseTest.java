package com.eatz.application.restaurantUserType.usecases;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUserType.exceptions.RestaurantUserTypeAlreadyExistsException;
import com.eatz.helper.RestaurantUserTypeHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUserTypeUseCaseTest {

    @Mock
    private RestaurantUserTypeRepository repository;

    private CreateRestaurantUserTypeUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateRestaurantUserTypeUseCase(repository);
    }

    @Test
    void shouldCreateRestaurantUserTypeSuccessfully() {
        // Given
        RestaurantUserType input = RestaurantUserTypeHelper.createRestaurantUserType(null, "MANAGER", "Gerente do restaurante");
        RestaurantUserType expected = RestaurantUserTypeHelper.createRestaurantUserType(1L, "MANAGER", "Gerente do restaurante");
        
        when(repository.existsByName("MANAGER")).thenReturn(false);
        when(repository.save(any(RestaurantUserType.class))).thenReturn(expected);

        // When
        RestaurantUserType result = useCase.execute(input);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("MANAGER");
        assertThat(result.getDescription()).isEqualTo("Gerente do restaurante");
        assertThat(result.isDeleted()).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenRestaurantUserTypeIsNull() {
        // When & Then
        assertThatThrownBy(() -> useCase.execute(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Restaurant user type cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // Given
        RestaurantUserType input = RestaurantUserTypeHelper.createRestaurantUserType(null, null, "Description");

        // When & Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Restaurant user type name cannot be null or empty");
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        // Given
        RestaurantUserType input = RestaurantUserTypeHelper.createRestaurantUserType(null, "", "Description");

        // When & Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Restaurant user type name cannot be null or empty");
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExists() {
        // Given
        RestaurantUserType input = RestaurantUserTypeHelper.createRestaurantUserType(null, "ADMIN", "Description");
        
        when(repository.existsByName("ADMIN")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(RestaurantUserTypeAlreadyExistsException.class)
                .hasMessage("Restaurant user type already exists with name: ADMIN");
    }
} 
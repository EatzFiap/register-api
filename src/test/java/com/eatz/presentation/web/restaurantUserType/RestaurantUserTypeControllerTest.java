package com.eatz.presentation.web.restaurantUserType;

import com.eatz.application.restaurantUserType.RestaurantUserTypeService;
import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeRequest;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeResponse;
import com.eatz.helper.RestaurantUserTypeHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUserTypeControllerTest {

    @Mock
    private RestaurantUserTypeService service;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RestaurantUserTypeController(service)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldFindAllRestaurantUserTypes() throws Exception {
        // Given
        List<RestaurantUserType> types = Arrays.asList(
                RestaurantUserTypeHelper.createRestaurantUserType(1L, "ADMIN", "Administrador"),
                RestaurantUserTypeHelper.createRestaurantUserType(2L, "MANAGER", "Gerente")
        );
        when(service.getAll()).thenReturn(types);

        // When & Then
        mockMvc.perform(get("/api/restaurant-user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("ADMIN"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("MANAGER"));
    }

    @Test
    void shouldFindRestaurantUserTypeById() throws Exception {
        // Given
        RestaurantUserType type = RestaurantUserTypeHelper.createRestaurantUserType(1L, "ADMIN", "Administrador");
        when(service.getById(1L)).thenReturn(type);

        // When & Then
        mockMvc.perform(get("/api/restaurant-user-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ADMIN"))
                .andExpect(jsonPath("$.description").value("Administrador"));
    }

    @Test
    void shouldCreateRestaurantUserType() throws Exception {
        // Given
        RestaurantUserTypeRequest request = new RestaurantUserTypeRequest("EMPLOYEE", "Funcionário");
        RestaurantUserType createdType = RestaurantUserTypeHelper.createRestaurantUserType(1L, "EMPLOYEE", "Funcionário");
        
        when(service.create(any(RestaurantUserType.class))).thenReturn(createdType);

        // When & Then
        mockMvc.perform(post("/api/restaurant-user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("EMPLOYEE"))
                .andExpect(jsonPath("$.description").value("Funcionário"));
    }

    @Test
    void shouldUpdateRestaurantUserType() throws Exception {
        // Given
        RestaurantUserTypeRequest request = new RestaurantUserTypeRequest("MANAGER", "Gerente atualizado");
        RestaurantUserType updatedType = RestaurantUserTypeHelper.createRestaurantUserType(1L, "MANAGER", "Gerente atualizado");
        
        when(service.update(eq(1L), any(RestaurantUserType.class))).thenReturn(updatedType);

        // When & Then
        mockMvc.perform(put("/api/restaurant-user-types/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("MANAGER"))
                .andExpect(jsonPath("$.description").value("Gerente atualizado"));
    }

    @Test
    void shouldDeleteRestaurantUserType() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/restaurant-user-types/1"))
                .andExpect(status().isNoContent());
    }
} 
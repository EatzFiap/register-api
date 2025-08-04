package com.eatz.helper;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.infrastructure.persistence.entity.RestaurantUserTypeEntity;

import java.time.LocalDateTime;

public class RestaurantUserTypeHelper {
    
    public static RestaurantUserType createRestaurantUserType() {
        return new RestaurantUserType(
                1L,
                "ADMIN",
                "Administrador com acesso total ao sistema",
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );
    }
    
    public static RestaurantUserType createRestaurantUserType(Long id, String name, String description) {
        return new RestaurantUserType(
                id,
                name,
                description,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );
    }
    
    public static RestaurantUserTypeEntity createRestaurantUserTypeEntity() {
        return new RestaurantUserTypeEntity(
                1L,
                "ADMIN",
                "Administrador com acesso total ao sistema",
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );
    }
    
    public static RestaurantUserTypeEntity createRestaurantUserTypeEntity(Long id, String name, String description) {
        return new RestaurantUserTypeEntity(
                id,
                name,
                description,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );
    }
} 
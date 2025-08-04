package com.eatz.infrastructure.persistence.mapper;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.infrastructure.persistence.entity.RestaurantUserTypeEntity;

public class RestaurantUserTypeEntityMapper {

    public static RestaurantUserType toDomain(RestaurantUserTypeEntity entity) {
        if (entity == null) {
            return null;
        }

        return new RestaurantUserType(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isDeleted()
        );
    }

    public static RestaurantUserTypeEntity toEntity(RestaurantUserType domain) {
        if (domain == null) {
            return null;
        }

        return new RestaurantUserTypeEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getCreatedAt(),
                domain.getUpdatedAt(),
                domain.isDeleted()
        );
    }
} 
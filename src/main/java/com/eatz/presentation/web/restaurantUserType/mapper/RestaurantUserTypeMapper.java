package com.eatz.presentation.web.restaurantUserType.mapper;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeRequest;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeResponse;

public class RestaurantUserTypeMapper {

    public static RestaurantUserType toDomain(RestaurantUserTypeRequest request) {
        if (request == null) {
            return null;
        }

        RestaurantUserType domain = new RestaurantUserType();
        domain.setName(request.getName());
        domain.setDescription(request.getDescription());
        return domain;
    }

    public static RestaurantUserTypeResponse toResponse(RestaurantUserType domain) {
        if (domain == null) {
            return null;
        }

        return new RestaurantUserTypeResponse(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
} 
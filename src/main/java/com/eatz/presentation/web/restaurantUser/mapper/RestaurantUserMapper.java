package com.eatz.presentation.web.restaurantUser.mapper;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserRequest;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserResponse;
import com.eatz.presentation.web.restaurantUser.dto.UpdateRestaurantUserRequest;
import org.springframework.stereotype.Component;

@Component
public class RestaurantUserMapper {

    public RestaurantUser toDomain(RestaurantUserRequest dto) {
        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setName(dto.getName());
        restaurantUser.setEmail(dto.getEmail());
        restaurantUser.setPassword(dto.getPassword());
        restaurantUser.setPhone(dto.getPhone());
        restaurantUser.setCpf(dto.getCpf());
        restaurantUser.setProfileImageUrl(dto.getProfileImageUrl());
        restaurantUser.setRestaurantId(dto.getRestaurantId());
        restaurantUser.setRestaurantUserTypeId(dto.getRestaurantUserTypeId());
        restaurantUser.setAddress(dto.getAddress());

        if (dto.getRole() != null) {
            restaurantUser.setRole(RestaurantRole.valueOf(dto.getRole()));
        }

        return restaurantUser;
    }

    public RestaurantUser toDomain(UpdateRestaurantUserRequest dto) {
        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setName(dto.getName());
        restaurantUser.setEmail(dto.getEmail());
        restaurantUser.setPhone(dto.getPhone());
        restaurantUser.setCpf(dto.getCpf());
        restaurantUser.setProfileImageUrl(dto.getProfileImageUrl());
        restaurantUser.setRestaurantUserTypeId(dto.getRestaurantUserTypeId());
        restaurantUser.setAddress(dto.getAddress());

        if (dto.getRole() != null) {
            restaurantUser.setRole(RestaurantRole.valueOf(dto.getRole()));
        }

        return restaurantUser;
    }

    public RestaurantUserResponse toResponse(RestaurantUser restaurantUser) {
        return new RestaurantUserResponse(restaurantUser);
    }
}
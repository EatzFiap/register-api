package com.eatz.infrastructure.persistence.restaurantUser;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.infrastructure.persistence.address.AddressEntityMapper;
import com.eatz.infrastructure.persistence.mapper.RestaurantUserTypeEntityMapper;

public class RestaurantUserEntityMapper {

    private RestaurantUserEntityMapper() {}

    public static RestaurantUserEntity toEntity(RestaurantUser user) {
        if (user == null) return null;

        RestaurantUserEntity entity = new RestaurantUserEntity();
        entity.setId(user.getId());
        entity.setRole(user.getRole() != null ? user.getRole().name() : null);
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setCpf(user.getCpf());
        entity.setPhone(user.getPhone());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        entity.setDeleted(user.isDeleted());
        entity.setProfileImageUrl(user.getProfileImageUrl());
        entity.setRestaurantId(user.getRestaurantId());
        entity.setRestaurantUserTypeId(user.getRestaurantUserTypeId());
        entity.setRestaurantUserType(RestaurantUserTypeEntityMapper.toEntity(user.getRestaurantUserType()));
        entity.setAddress(AddressEntityMapper.toEntity(user.getAddress()));
        return entity;
    }

    public static RestaurantUser toDomain(RestaurantUserEntity entity) {
        if (entity == null) return null;

        RestaurantUser user = new RestaurantUser();
        user.setId(entity.getId());
        user.setRole(entity.getRole() != null ? RestaurantRole.valueOf(entity.getRole()) : null);
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setCpf(entity.getCpf());
        user.setPhone(entity.getPhone());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        user.setDeleted(entity.isDeleted());
        user.setProfileImageUrl(entity.getProfileImageUrl());
        user.setRestaurantId(entity.getRestaurantId());
        user.setRestaurantUserTypeId(entity.getRestaurantUserTypeId());
        user.setRestaurantUserType(RestaurantUserTypeEntityMapper.toDomain(entity.getRestaurantUserType()));

        if (entity.getAddress() != null && !entity.getAddress().isDeleted()) {
            user.setAddress(AddressEntityMapper.toDomain(entity.getAddress()));
        }

        return user;
    }
}
package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.restaurant.Restaurant;
import com.eatz.infrastructure.persistence.address.AddressEntityMapper;
import com.eatz.infrastructure.persistence.restaurantUser.RestaurantUserEntityMapper;

public class RestaurantEntityMapper {

    private RestaurantEntityMapper() {}

    public static Restaurant toDomain(RestaurantEntity entity) {
        if (entity == null) return null;

        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getLogoImageUrl(),
                AddressEntityMapper.toDomain(entity.getAddress()),
                entity.getPhone(),
                entity.getWhatsappPhone(),
                entity.getCnpj(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getIsDeleted(),
                entity.getDeliveryRadius(),
                RestaurantUserEntityMapper.toDomain(entity.getOwner())
        );
    }

    public static RestaurantEntity toEntity(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restaurant.getId());
        entity.setName(restaurant.getName());
        entity.setLogoImageUrl(restaurant.getLogoImageUrl());
        entity.setAddress(AddressEntityMapper.toEntity(restaurant.getAddress()));
        entity.setPhone(restaurant.getPhone());
        entity.setWhatsappPhone(restaurant.getWhatsappPhone());
        entity.setCnpj(restaurant.getCnpj());
        entity.setCreatedAt(restaurant.getCreatedAt());
        entity.setUpdatedAt(restaurant.getUpdatedAt());
        entity.setIsDeleted(restaurant.getIsDeleted());
        entity.setDeliveryRadius(restaurant.getDeliveryRadius());
        entity.setOwner(RestaurantUserEntityMapper.toEntity(restaurant.getOwner()));
        return entity;
    }
}

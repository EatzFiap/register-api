package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.restaurant.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityMapper {

    public RestaurantEntityMapper(AddressEntityMapper addressMapper) {
    }

    public Restaurant toDomain(RestaurantEntity entity) {
        if (entity == null) return null;

        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getLogoImageUrl(),
                entity.getPhone(),
                entity.getWhatsappPhone(),
                entity.getCnpj(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getIsDeleted(),
                entity.getDeliveryRadius(),
                AddressEntityMapper.toDomain(entity.getAddress())
        );
    }

    public RestaurantEntity toEntity(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restaurant.getId());
        entity.setName(restaurant.getName());
        entity.setAddress(AddressEntityMapper.toEntity(restaurant.getAddress()));

        return entity;
    }
}

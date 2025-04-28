package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.restaurantuser.RestaurantUser;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityMapper {

    public RestaurantEntityMapper(AddressEntityMapper addressMapper) {
    }

    public RestaurantUser toDomain(RestaurantEntity entity) {
        if (entity == null) return null;

        return new RestaurantUser(
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

    public RestaurantEntity toEntity(RestaurantUser restaurantUser) {
        if (restaurantUser == null) return null;

        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restaurantUser.getId());
        entity.setName(restaurantUser.getName());
        entity.setAddress(AddressEntityMapper.toEntity(restaurantUser.getAddress()));

        return entity;
    }
}

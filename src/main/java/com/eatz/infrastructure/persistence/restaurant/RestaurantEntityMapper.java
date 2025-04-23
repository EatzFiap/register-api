
package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.restaurant.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityMapper {

    public Restaurant toDomain(RestaurantEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getPassword(),
                entity.isActive()
        );
    }

    public RestaurantEntity toEntity(Restaurant restaurant) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restaurant.getId());
        entity.setName(restaurant.getName());
        entity.setEmail(restaurant.getEmail());
        entity.setLogin(restaurant.getLogin());
        entity.setPassword(restaurant.getPassword());
        entity.setActive(restaurant.isActive());
        return entity;
    }
}
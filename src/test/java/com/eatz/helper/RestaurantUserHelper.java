package com.eatz.helper;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.infrastructure.persistence.restaurantUser.RestaurantUserEntity;

import java.time.LocalDateTime;

import static com.eatz.helper.AddressHelper.createAddress;

public abstract class RestaurantUserHelper {

    public static RestaurantUser createRestaurantUser() {
        RestaurantUser user = new RestaurantUser();
        user.setId(1L);
        user.setRole(RestaurantRole.MANAGER);
        user.setName("Maria Oliveira");
        user.setEmail("maria.oliveira@email.com");
        user.setCpf("12345678900");
        user.setPhone("11999999999");
        user.setCreatedAt(LocalDateTime.now());
        user.setDeleted(false);
        user.setProfileImageUrl("https://example.com/image.jpg");
        user.setRestaurantId(10L);
        user.setAddress(createAddress());
        return user;
    }

    public static RestaurantUserEntity createRestaurantUserEntity() {
        RestaurantUserEntity entity = new RestaurantUserEntity();
        entity.setId(1L);
        entity.setRole(RestaurantRole.MANAGER.name());
        entity.setName("Maria Oliveira");
        entity.setEmail("maria.oliveira@email.com");
        entity.setPassword("password123");
        entity.setCpf("12345678900");
        entity.setPhone("11999999999");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setDeleted(false);
        entity.setProfileImageUrl("https://example.com/image.jpg");
        entity.setRestaurantId(10L);
        entity.setAddress(AddressHelper.createAddressEntity());
        return entity;
    }

}

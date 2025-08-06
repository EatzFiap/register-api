package com.eatz.presentation.web.restaurant.mapper;

import com.eatz.domain.address.Address;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.presentation.web.address.mapper.AddressMapper;
import com.eatz.presentation.web.restaurant.dto.RestaurantRequest;
import com.eatz.presentation.web.restaurant.dto.RestaurantResponse;

public class RestaurantMapper {

    public static Restaurant toDomain(RestaurantRequest request) {
        RestaurantUser owner = new RestaurantUser();
        owner.setId(request.getOwnerId());

        Address address = AddressMapper.toDomain(request.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setLogoImageUrl(request.getLogoImageUrl());
        restaurant.setAddress(address);
        restaurant.setPhone(request.getPhone());
        restaurant.setWhatsappPhone(request.getWhatsappPhone());
        restaurant.setCnpj(request.getCnpj());
        restaurant.setDeliveryRadius(request.getDeliveryRadius());
        restaurant.setOwner(owner);

        return restaurant;
    }

    public static RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLogoImageUrl(),
                AddressMapper.toResponse(restaurant.getAddress()),
                restaurant.getPhone(),
                restaurant.getWhatsappPhone(),
                restaurant.getCnpj(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt(),
                restaurant.getIsDeleted(),
                restaurant.getDeliveryRadius(),
                restaurant.getOwner() != null ? restaurant.getOwner().getId() : null
        );
    }
}


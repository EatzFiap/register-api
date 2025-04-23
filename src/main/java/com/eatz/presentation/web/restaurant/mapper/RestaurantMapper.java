package com.eatz.presentation.web.restaurant.mapper;

import com.eatz.domain.address.Address;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.presentation.web.address.dto.AddressResponse;
import com.eatz.presentation.web.restaurant.dto.RestaurantRequest;
import com.eatz.presentation.web.restaurant.dto.RestaurantResponse;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public Restaurant toDomain(RestaurantRequest dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setEmail(dto.getEmail());
        restaurant.setPassword(dto.getPassword());

        Address e = new Address();
        e.setStreet(dto.getAddress().getStreet());
        e.setCity(dto.getAddress().getCity());
        e.setState(dto.getAddress().getState());
        e.setZipCode(dto.getAddress().getZipCode());


        restaurant.setAddress(e);
        return restaurant;
    }

    public RestaurantResponse toResponse(Restaurant restaurant) {
        AddressResponse endereco = new AddressResponse(
                restaurant.getAddress().getState(),
                restaurant.getAddress().getCity(),
                restaurant.getAddress().getState(),
                restaurant.getAddress().getZipCode()
        );

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getEmail(),
                endereco
        );
    }
}

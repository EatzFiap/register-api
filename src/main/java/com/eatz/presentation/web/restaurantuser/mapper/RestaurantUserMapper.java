package com.eatz.presentation.web.restaurantuser.mapper;

import com.eatz.domain.address.Address;
import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.presentation.web.address.dto.AddressResponse;
import com.eatz.presentation.web.restaurantuser.dto.RestaurantUserRequest;
import com.eatz.presentation.web.restaurantuser.dto.RestaurantUserResponse;
import org.springframework.stereotype.Component;

@Component
public class RestaurantUserMapper {
    public RestaurantUser toDomain(RestaurantUserRequest dto) {
        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setName(dto.getName());
        restaurantUser.setEmail(dto.getEmail());
        restaurantUser.setPassword(dto.getPhone());

        Address e = new Address();
        e.setStreet(dto.getAddress().getStreet());
        e.setCity(dto.getAddress().getCity());
        e.setState(dto.getAddress().getState());
        e.setZipCode(dto.getAddress().getZipCode());


        restaurantUser.setAddress(e);
        return restaurantUser;
    }

    public RestaurantUserResponse toResponse(RestaurantUser restaurantUser) {
        AddressResponse address = new AddressResponse(
                restaurantUser.getAddress().getState(),
                restaurantUser.getAddress().getCity(),
                restaurantUser.getAddress().getState(),
                restaurantUser.getAddress().getZipCode()
        );

        return new RestaurantUserResponse(
                restaurantUser.getId(),
                restaurantUser.getName(),
                restaurantUser.getEmail(),
                address
        );
    }
}

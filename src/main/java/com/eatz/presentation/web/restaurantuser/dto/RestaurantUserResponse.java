package com.eatz.presentation.web.restaurantuser.dto;

import com.eatz.presentation.web.address.dto.AddressResponse;

import java.util.UUID;

public class RestaurantUserResponse {
    private String name;
    private String email;
    private String password;
    private AddressResponse address;

    public RestaurantUserResponse(UUID id, String name, String email, AddressResponse address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}

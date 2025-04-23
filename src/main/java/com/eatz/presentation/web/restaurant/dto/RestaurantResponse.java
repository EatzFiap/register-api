package com.eatz.presentation.web.restaurant.dto;

import com.eatz.presentation.web.address.dto.AddressResponse;

import java.util.UUID;

public class RestaurantResponse {
    private String name;
    private String email;
    private String password;
    private AddressResponse address;

    public RestaurantResponse(UUID id, String name, String email, AddressResponse endereco) {
        this.name = name;
        this.email = email;
        this.address = endereco;
    }
}

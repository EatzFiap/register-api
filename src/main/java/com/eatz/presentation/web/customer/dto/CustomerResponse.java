package com.eatz.presentation.web.customer.dto;

import com.eatz.presentation.web.address.dto.AddressResponse;

import java.util.UUID;

public class CustomerResponse {
    private String name;
    private String email;
    private String password;
    private AddressResponse address;

    public CustomerResponse(Integer id, String name, String email, AddressResponse endereco) {
        this.name = name;
        this.email = email;
        this.address = endereco;
    }
}

package com.eatz.presentation.web.customer.dto;

import com.eatz.presentation.web.address.dto.AddressResponse;

public class CustomerResponse {
    private String name;
    private String email;
    private String password;
    private AddressResponse address;

    public CustomerResponse(Long id, String name, String email, AddressResponse endereco) {
        this.name = name;
        this.email = email;
        this.address = endereco;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }
}

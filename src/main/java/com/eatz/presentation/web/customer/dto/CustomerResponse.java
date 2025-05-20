package com.eatz.presentation.web.customer.dto;

import com.eatz.presentation.web.address.dto.AddressResponse;

public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private AddressResponse address;

    public CustomerResponse(Long id, String name, String email, AddressResponse address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }
}

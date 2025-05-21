package com.eatz.presentation.web.customer.dto;

import com.eatz.presentation.web.address.dto.AddressResponse;

import java.util.List;

public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;
    private List<AddressResponse> addresses;

    public CustomerResponse(
            Long id,
            String name,
            String email,
            String createdAt,
            String updatedAt,
            List<AddressResponse> addresses
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.addresses = addresses;
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

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}

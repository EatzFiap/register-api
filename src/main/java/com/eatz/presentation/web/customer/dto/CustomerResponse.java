package com.eatz.presentation.web.customer.dto;

import com.eatz.domain.customer.Customer;
import com.eatz.presentation.web.address.dto.AddressResponse;

import java.util.List;

public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private String profileImageUrl;
    private String createdAt;
    private String updatedAt;
    private List<AddressResponse> addresses;

    public CustomerResponse(
            Customer customer,
            List<AddressResponse> addresses
    ) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.cpf = customer.getCpf();
        this.profileImageUrl = customer.getProfileImageUrl();
        this.createdAt = customer.getCreatedAt() != null ? customer.getCreatedAt() : null;
        this.updatedAt = customer.getUpdatedAt() != null ? customer.getUpdatedAt() : null;
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

    public String getPhone() {
        return phone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}

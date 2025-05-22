package com.eatz.domain.customer;

import com.eatz.domain.address.Address;
import com.eatz.infrastructure.persistence.customer.CustomerEntity;
import com.eatz.shared.domain.User;

import java.util.List;

public class Customer extends User {

    private List<Address> addresses;
    private String createdAt;
    private String updatedAt;

    public Customer(CustomerEntity entity, List<Address> addresses) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.cpf = entity.getCpf();
        this.phone = entity.getPhone();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.isDeleted = entity.isDeleted();
        this.profileImageUrl = entity.getProfileImageUrl();
        this.addresses = addresses;
    }

    public Customer() {
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
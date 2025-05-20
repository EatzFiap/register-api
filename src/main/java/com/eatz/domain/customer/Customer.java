package com.eatz.domain.customer;

import com.eatz.domain.address.Address;
import com.eatz.shared.domain.User;

import java.util.List;

public class Customer extends User {

    private List<Address> addresses;
    private String createdAt;
    private String updatedAt;

    public Customer(Long id, String name, String email, String password, String cpf, String phone, String createdAt, String updatedAt, boolean isDeleted, String profileImageUrl, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.profileImageUrl = profileImageUrl;
        this.addresses = addresses;
    }

    public Customer(Long id, String name, String email, String password, String cpf, String phone, String createdAt, String updatedAt, boolean isDeleted, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.profileImageUrl = profileImageUrl;
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
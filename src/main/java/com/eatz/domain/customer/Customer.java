package com.eatz.domain.customer;

import com.eatz.domain.address.Address;

import java.util.List;

public class Customer {

    private Integer idCustomerUser;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;
    private String createdAt;
    private String updatedAt;
    private boolean isDeleted;
    private String profileImageUrl;

    private List<Address> addresses;

    public Customer(Integer idCustomerUser, String name, String email, String password, String cpf, String phone, String createdAt, String updatedAt, boolean isDeleted, String profileImageUrl, List<Address> addresses) {
        this.idCustomerUser = idCustomerUser;
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

    public Customer() {
    }

    // Getters and setters

    public Integer getIdCustomerUser() {
        return idCustomerUser;
    }

    public void setIdCustomerUser(Integer idCustomerUser) {
        this.idCustomerUser = idCustomerUser;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
package com.eatz.presentation.web.restaurantUser.dto;

import com.eatz.domain.address.Address;
import jakarta.validation.constraints.NotBlank;

public class UpdateRestaurantUserRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String role; // Deve ser uma das opções do enum: ADMIN, MANAGER, EMPLOYEE

    private String cpf;

    private String phone;

    private String profileImageUrl;

    private Long restaurantUserTypeId;

    private Address address;

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
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public Long getRestaurantUserTypeId() {
        return restaurantUserTypeId;
    }
    public void setRestaurantUserTypeId(Long restaurantUserTypeId) {
        this.restaurantUserTypeId = restaurantUserTypeId;
    }
}
package com.eatz.presentation.web.restaurantUser.dto;

import com.eatz.domain.address.Address;
import jakarta.validation.constraints.NotBlank;

public class RestaurantUserRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String role; // Deve ser uma das opções do enum: ADMIN, MANAGER, EMPLOYEE

    private String password;

    private String cpf;

    private String phone;

    private String profileImageUrl;

    private Long restaurantId;

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
    public Long getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
    public Address getAddress() {
        return address;
    }
}
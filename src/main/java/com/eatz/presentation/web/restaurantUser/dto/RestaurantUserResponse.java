package com.eatz.presentation.web.restaurantUser.dto;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.presentation.web.address.dto.AddressResponse;

public class RestaurantUserResponse {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String role;
    private String profileImageUrl;
    private Long restaurantId;
    private AddressResponse address;

    public RestaurantUserResponse(RestaurantUser restaurantUser) {
        this.id = restaurantUser.getId();
        this.name = restaurantUser.getName();
        this.email = restaurantUser.getEmail();
        this.cpf = restaurantUser.getCpf();
        this.phone = restaurantUser.getPhone();
        this.role = restaurantUser.getRole() != null ? restaurantUser.getRole().name() : null;
        this.profileImageUrl = restaurantUser.getProfileImageUrl();
        this.restaurantId = restaurantUser.getRestaurantId();
        this.address = restaurantUser.getAddress() != null ? new AddressResponse(restaurantUser.getAddress()) : null;
    }

    // Getters e Setters

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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public Long getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
    public AddressResponse getAddress() {
        return address;
    }
}
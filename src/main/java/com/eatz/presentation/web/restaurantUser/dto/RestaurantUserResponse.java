package com.eatz.presentation.web.restaurantUser.dto;

public class RestaurantUserResponse {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String role;
    private String profileImageUrl;
    private Long restaurantId;

    public RestaurantUserResponse(
            Long id,
            String name,
            String email,
            String cpf,
            String phone,
            String role,
            String profileImageUrl,
            Long restaurantId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
        this.role = role;
        this.profileImageUrl = profileImageUrl;
        this.restaurantId = restaurantId;
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
}
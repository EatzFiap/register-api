package com.eatz.domain.restaurantUser;

import com.eatz.domain.address.Address;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.shared.domain.User;

import java.time.LocalDateTime;

public class RestaurantUser extends User {

    private RestaurantRole role;
    private Long restaurantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Address address;

    public RestaurantUser(Long id, RestaurantRole role, String name, String email, String password, String cpf, String phone, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted, String profileImageUrl, Long restaurantId) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.profileImageUrl = profileImageUrl;
        this.restaurantId = restaurantId;
    }

    public RestaurantUser() {
    }

    public RestaurantRole getRole() {
        return role;
    }

    public void setRole(RestaurantRole role) {
        this.role = role;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
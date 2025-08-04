package com.eatz.domain.restaurant;

import com.eatz.domain.address.Address;
import com.eatz.domain.restaurantUser.RestaurantUser;

import java.time.LocalDateTime;

public class Restaurant {

    private Long id;
    private String name;
    private String logoImageUrl;
    private Address address;
    private String phone;
    private String whatsappPhone;
    private String cnpj;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
    private Double deliveryRadius;
    private RestaurantUser owner;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String logoImageUrl, Address address, String phone, String whatsappPhone, String cnpj, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isDeleted, Double deliveryRadius, RestaurantUser owner) {
        this.id = id;
        this.name = name;
        this.logoImageUrl = logoImageUrl;
        this.address = address;
        this.phone = phone;
        this.whatsappPhone = whatsappPhone;
        this.cnpj = cnpj;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.deliveryRadius = deliveryRadius;
        this.owner = owner;
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

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWhatsappPhone() {
        return whatsappPhone;
    }

    public void setWhatsappPhone(String whatsappPhone) {
        this.whatsappPhone = whatsappPhone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Double getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(Double deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public RestaurantUser getOwner() {
        return owner;
    }

    public void setOwner(RestaurantUser owner) {
        this.owner = owner;
    }
}
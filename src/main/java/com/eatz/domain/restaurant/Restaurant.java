package com.eatz.domain.restaurant;

import com.eatz.domain.address.Address;

import java.time.LocalDateTime;
import java.util.UUID;

public class Restaurant {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private String logoImageUrl;
    private String phone;
    private String whatsappPhone;
    private String cnpj;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;
    private Double deliveryRadius;
    private Address address;

    public Restaurant() {
    }

    public Restaurant(UUID id, String name, String logoImageUrl, String phone, String whatsappPhone, String cnpj,
                      LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted, Double deliveryRadius, Address address) {
        this.id = id;
        this.name = name;
        this.logoImageUrl = logoImageUrl;
        this.phone = phone;
        this.whatsappPhone = whatsappPhone;
        this.cnpj = cnpj;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.deliveryRadius = deliveryRadius;
        this.address = address;
    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
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

    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Double getDeliveryRadius() {
        return deliveryRadius;
    }
    public void setDeliveryRadius(Double deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
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
}
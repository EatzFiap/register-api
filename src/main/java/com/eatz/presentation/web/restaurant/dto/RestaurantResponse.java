package com.eatz.presentation.web.restaurant.dto;

import com.eatz.presentation.web.address.dto.AddressResponse;

import java.time.LocalDateTime;

public class RestaurantResponse {

    private Long id;
    private String name;
    private String logoImageUrl;
    private AddressResponse address;
    private String phone;
    private String whatsappPhone;
    private String cnpj;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
    private Double deliveryRadius;
    private Long ownerId;

    public RestaurantResponse() {
    }

    public RestaurantResponse(Long id, String name, String logoImageUrl, AddressResponse address, String phone, String whatsappPhone, String cnpj, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isDeleted, Double deliveryRadius, Long ownerId) {
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
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWhatsappPhone() {
        return whatsappPhone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public Double getDeliveryRadius() {
        return deliveryRadius;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}

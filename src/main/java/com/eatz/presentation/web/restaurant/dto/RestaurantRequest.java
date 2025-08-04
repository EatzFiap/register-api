package com.eatz.presentation.web.restaurant.dto;

import com.eatz.presentation.web.address.dto.AddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RestaurantRequest {

    @NotBlank
    private String name;

    @Size(max = 500)
    private String logoImageUrl;

    @NotNull
    private AddressRequest address;

    @NotBlank
    private String phone;

    private String whatsappPhone;

    @NotBlank
    private String cnpj;

    private Double deliveryRadius;

    @NotNull
    private Long ownerId;

    public RestaurantRequest() {
    }

    public RestaurantRequest(String name, String logoImageUrl, AddressRequest address, String phone, String whatsappPhone, String cnpj, Double deliveryRadius, Long ownerId) {
        this.name = name;
        this.logoImageUrl = logoImageUrl;
        this.address = address;
        this.phone = phone;
        this.whatsappPhone = whatsappPhone;
        this.cnpj = cnpj;
        this.deliveryRadius = deliveryRadius;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public AddressRequest getAddress() {
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

    public Double getDeliveryRadius() {
        return deliveryRadius;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}

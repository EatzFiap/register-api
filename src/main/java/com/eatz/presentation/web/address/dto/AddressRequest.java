package com.eatz.presentation.web.address.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressRequest {
    @NotBlank
    private String street;

    private String number;

    private String complement;

    @NotBlank
    private String city;

    @NotBlank
    private String neighbourhood;

    @NotBlank
    private String state;

    @NotBlank
    private String zipCode;

    private String nickname;

    private boolean defaultAddress;

    public @NotBlank String getStreet() {
        return street;
    }

    public void setStreet(@NotBlank String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public @NotBlank String getCity() {
        return city;
    }

    public void setCity(@NotBlank String city) {
        this.city = city;
    }

    public @NotBlank String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(@NotBlank String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public @NotBlank String getState() {
        return state;
    }

    public void setState(@NotBlank String state) {
        this.state = state;
    }

    public @NotBlank String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@NotBlank String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}

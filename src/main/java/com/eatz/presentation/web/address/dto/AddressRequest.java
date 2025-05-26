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

    private boolean isDefault;

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getNickname() { return nickname; }
    public boolean isDefault() { return isDefault; }
    public String getNumber() { return number; }
    public String getComplement() { return complement; }
    public @NotBlank String getNeighbourhood() { return neighbourhood; }
}

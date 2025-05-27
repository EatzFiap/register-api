package com.eatz.presentation.web.address.dto;

public class CustomerAddressResponse extends AddressResponse {
    private String nickname;
    private boolean isDefault;

    public CustomerAddressResponse(
            Long id,
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String nickname,
            boolean isDefault) {
        super(id, street, number, complement, neighborhood, city, state, zipCode);
        this.nickname = nickname;
        this.isDefault = isDefault;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isDefault() {
        return isDefault;
    }
}

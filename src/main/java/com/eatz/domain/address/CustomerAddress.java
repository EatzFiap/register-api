package com.eatz.domain.address;


import com.eatz.presentation.web.address.dto.AddressRequest;

public class CustomerAddress extends Address {

    private String nickname;
    private boolean isDefault;

    public CustomerAddress() {
    }

    public CustomerAddress(Long id, String street, String number, String complement, String city, String neighbourhood, String state, String zipCode, String nickname, boolean isDefault) {
        super(id, street, number, complement, city, neighbourhood, state, zipCode);
        this.nickname = nickname;
        this.isDefault = isDefault;
    }

    public CustomerAddress(Address savedAddress, AddressRequest addressRequest) {
        super(savedAddress.getId(), addressRequest.getStreet(), addressRequest.getNumber(), addressRequest.getComplement(),
                addressRequest.getCity(), addressRequest.getNeighbourhood(), addressRequest.getState(), addressRequest.getZipCode());
        this.nickname = addressRequest.getNickname();
        this.isDefault = addressRequest.isDefault();
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isDefault() {
        return isDefault;
    }

}

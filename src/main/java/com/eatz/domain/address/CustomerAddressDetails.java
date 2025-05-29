package com.eatz.domain.address;


import com.eatz.presentation.web.address.dto.AddressRequest;

public class CustomerAddressDetails extends Address {

    private String nickname;
    private boolean isDefault;

    public CustomerAddressDetails() {
    }

    public CustomerAddressDetails(Long id, String street, String number, String complement, String city, String neighbourhood, String state, String zipCode, String nickname, boolean isDefault) {
        super(id, street, number, complement, city, neighbourhood, state, zipCode);
        this.nickname = nickname;
        this.isDefault = isDefault;
    }

    public CustomerAddressDetails(Address savedAddress, AddressRequest addressRequest) {
        super(savedAddress.getId(), addressRequest.getStreet(), addressRequest.getNumber(), addressRequest.getComplement(),
                addressRequest.getCity(), addressRequest.getNeighbourhood(), addressRequest.getState(), addressRequest.getZipCode());
        this.nickname = addressRequest.getNickname();
        this.isDefault = addressRequest.isDefaultAddress();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}

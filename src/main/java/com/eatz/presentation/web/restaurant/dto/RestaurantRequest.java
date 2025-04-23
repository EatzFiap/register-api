package com.eatz.presentation.web.restaurant.dto;


import com.eatz.presentation.web.address.dto.AddressRequest;

public class RestaurantRequest {
    private String name;
    private String email;
    private String password;
    private AddressRequest address;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public AddressRequest getAddress() {
        return address;
    }
    public void setAddress(AddressRequest address) {
        this.address = address;
    }

}

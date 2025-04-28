package com.eatz.presentation.web.restaurantuser.dto;


import com.eatz.presentation.web.address.dto.AddressRequest;

public class RestaurantUserRequest {
    private String name;
    private String email;
    private String phone;
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public AddressRequest getAddress() {
        return address;
    }
    public void setAddress(AddressRequest address) {
        this.address = address;
    }

}

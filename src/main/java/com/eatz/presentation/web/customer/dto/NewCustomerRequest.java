package com.eatz.presentation.web.customer.dto;


import com.eatz.presentation.web.address.dto.AddressRequest;
import jakarta.validation.constraints.NotNull;

public class NewCustomerRequest {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String phone;
    private String cpf;
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
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}

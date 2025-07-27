package com.eatz.presentation.web.customer.dto;

public class UpdateCustomerRequest {
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private String profileImageUrl;

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
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

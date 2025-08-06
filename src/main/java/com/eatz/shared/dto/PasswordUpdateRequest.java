package com.eatz.shared.dto;

import jakarta.validation.constraints.NotBlank;

public class PasswordUpdateRequest {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    public PasswordUpdateRequest() {
    }

    public PasswordUpdateRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}

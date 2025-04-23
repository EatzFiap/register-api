package com.eatz.presentation.web.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email(message = "${validation.invalid}")
        @Size(min = 3, max = 255, message = "${validation.size}")
        @NotBlank(message = "${validation.notBlank}")
        String email,
        @NotBlank(message = "${validation.notBlank}")
        @Size(min = 6, max = 255, message = "${validation.size}")
        String password
) {
}

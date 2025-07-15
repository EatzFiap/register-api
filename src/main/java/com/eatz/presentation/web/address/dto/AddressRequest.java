package com.eatz.presentation.web.address.dto;

public record AddressRequest(
        String street,
        String city,
        String state,
        String zipCode
) {}
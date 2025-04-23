package com.eatz.presentation.web.customer.dto;

import java.util.Date;

public record AuthenticationResponse(
        String token,
        String type,
        Date expiresAt,
        String username
) {}

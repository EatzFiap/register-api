package com.eatz.presentation.web.restaurantUser.dto;

import java.util.Date;

public record AuthenticationResponse(
        String token,
        String type,
        Date expiresAt,
        String username
) {}

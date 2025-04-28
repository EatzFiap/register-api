package com.eatz.presentation.web.restaurantuser.dto;

import java.util.Date;

public record AuthenticationResponse(
        String token,
        String type,
        Date expiresAt,
        String username
) {}

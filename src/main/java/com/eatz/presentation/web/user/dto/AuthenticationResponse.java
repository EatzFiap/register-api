package com.eatz.presentation.web.user.dto;

import java.util.Date;

public record AuthenticationResponse(
        String token,
        String type,
        Date expiresAt,
        String username
) {}

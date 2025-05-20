package com.eatz.shared.dto;

import java.util.Date;

public record AuthenticationResponse(
        String token,
        String type,
        Date expiresAt,
        String username
) {}

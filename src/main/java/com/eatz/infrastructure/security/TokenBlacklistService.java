package com.eatz.infrastructure.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Map<String, Long> revokedTokens = new ConcurrentHashMap<>();

    public void revokeToken(String token, long expirationTime) {
        revokedTokens.put(token, expirationTime);
    }

    public boolean isTokenRevoked(String token) {
        Long expirationTime = revokedTokens.get(token);
        if (expirationTime == null) {
            return false;
        }

        if (System.currentTimeMillis() > expirationTime) {
            revokedTokens.remove(token);
            return false;
        }
        return true;
    }
}



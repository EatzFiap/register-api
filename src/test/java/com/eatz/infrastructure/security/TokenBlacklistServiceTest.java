package com.eatz.infrastructure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TokenBlacklistServiceTest {

    @InjectMocks
    private TokenBlacklistService tokenBlacklistService;

    @Nested
    @DisplayName("Revoke Token")
    class RevokeToken {

        @Test
        @DisplayName("Revokes token successfully")
        void revokesTokenSuccessfully() {
            String token = "testToken";
            long expirationTime = System.currentTimeMillis() + 10000;

            tokenBlacklistService.revokeToken(token, expirationTime);

            assertThat(tokenBlacklistService.isTokenRevoked(token)).isTrue();
        }

        @Test
        @DisplayName("Returns false when token is not revoked")
        void returnsFalseWhenTokenIsNotRevoked() {
            String token = "nonRevokedToken";

            assertThat(tokenBlacklistService.isTokenRevoked(token)).isFalse();
        }

        @Test
        @DisplayName("Returns false when token is expired")
        void returnsFalseWhenTokenIsExpired() {
            String token = "expiredToken";
            long expirationTime = System.currentTimeMillis() - 10000;

            tokenBlacklistService.revokeToken(token, expirationTime);

            assertThat(tokenBlacklistService.isTokenRevoked(token)).isFalse();
        }

        @Test
        @DisplayName("Removes expired token from blacklist")
        void removesExpiredTokenFromBlacklist() {
            String token = "expiredTokenToRemove";
            long expirationTime = System.currentTimeMillis() - 10000;

            tokenBlacklistService.revokeToken(token, expirationTime);
            tokenBlacklistService.isTokenRevoked(token);

            assertThat(tokenBlacklistService.isTokenRevoked(token)).isFalse();
        }

    }

}
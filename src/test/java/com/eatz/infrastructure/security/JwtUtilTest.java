package com.eatz.infrastructure.security;

import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @Spy
    @InjectMocks
    private JwtUtil jwtUtil;

    private static final String SECRET = "71938420591730642710954873620914783649102837461098476320813749268103482917460";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expirationTime", 3600000L);
    }

    @Nested
    @DisplayName("Generate Customer User Token")
    class GenerateCustomerUserToken {

        @Test
        void generatesTokenSuccessfullyForValidUsername() {
            String username = "validUser@email.com";

            String token = jwtUtil.generateCustomerUserToken(username);

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            assertThat(claims.getSubject()).isEqualTo(username);
            assertThat(claims).containsEntry("userType","CUSTOMER");
        }

    }

    @Nested
    @DisplayName("Generate Restaurant User Token")
    class GenerateRestaurantUserToken {

        @Test
        void generatesTokenSuccessfullyForValidUsernameAndRole() {
            String username = "restaurantUser@email.com";
            RestaurantRole role = RestaurantRole.MANAGER;

            String token = jwtUtil.generateRestaurantUserToken(username, role);

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            assertThat(claims.getSubject()).isEqualTo(username);
            assertThat(claims)
                    .containsEntry("userType", "RESTAURANT_USER")
                    .containsEntry("role", role.name());
        }

        @Test
        void failsToGenerateTokenWhenRoleIsNull() {
            String username = "restaurantUser@email.com";

            assertThatThrownBy(() -> jwtUtil.generateRestaurantUserToken(username, null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void generatesTokenWithCorrectExpirationTime() {
            String username = "restaurantUser@email.com";
            RestaurantRole role = RestaurantRole.EMPLOYEE;

            String token = jwtUtil.generateRestaurantUserToken(username, role);

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date issuedAt = claims.getIssuedAt();
            Date expiration = claims.getExpiration();
            assertThat(expiration.getTime() - issuedAt.getTime()).isEqualTo(3600000L);
        }

    }

    @Nested
    @DisplayName("Extract User Type")
    class ExtractUserType {

        @Test
        @SuppressWarnings("deprecation")
        void extractsUserTypeSuccessfullyFromValidToken() {
            String token = Jwts.builder()
                    .claim("userType", "CUSTOMER")
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();

            String userType = jwtUtil.extractUserType(token);

            assertThat(userType).isEqualTo("CUSTOMER");
        }

        @Test
        void throwsExceptionForInvalidToken() {
            String invalidToken = "invalid.token.value";

            assertThatThrownBy(() -> jwtUtil.extractUserType(invalidToken))
                    .isInstanceOf(JwtException.class);
        }

    }

    @Nested
    @DisplayName("Validate Token")
    class ValidateToken {

        @Test
        void returnsTrueForValidTokenAndMatchingUsername() {
            String token = getToken();

            boolean isValid = jwtUtil.validateToken(token, "validUser@email.com");

            assertThat(isValid).isTrue();
        }

        @Test
        @SuppressWarnings("deprecation")
        void returnsFalseForTokenWithMismatchedUsername() {
            String token = Jwts.builder()
                    .subject("differentUser")
                    .expiration(new Date(System.currentTimeMillis() + 3600000L))
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();

            boolean isValid = jwtUtil.validateToken(token, "validUser@email.com");

            assertThat(isValid).isFalse();
        }

        @Test
        void returnsFalseForExpiredToken() {
            String token = getToken();

            doReturn(true).when(jwtUtil).isTokenExpired(token);

            boolean isValid = jwtUtil.validateToken(token, "validUser@email.com");

            assertThat(isValid).isFalse();
        }

        @Test
        void returnsFalseForRevokedToken() {
            String token = getToken();

            when(tokenBlacklistService.isTokenRevoked(token)).thenReturn(true);

            boolean isValid = jwtUtil.validateToken(token, "validUser@email.com");

            assertThat(isValid).isFalse();
        }

    }

    @SuppressWarnings("deprecation")
    private static String getToken() {
        return Jwts.builder()
                .subject("validUser@email.com")
                .expiration(new Date(System.currentTimeMillis() + 3600000L))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

}
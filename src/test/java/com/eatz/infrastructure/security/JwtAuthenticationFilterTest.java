package com.eatz.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CompositeUserDetailsService detailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    private static final String VALID_JWT_TOKEN = "valid.jwt.token";

    @BeforeEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void cleanUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticate_whenTokenIsValid() throws Exception {
        String username = "user@example.com";
        UserDetails userDetails = new User(username, "password", List.of());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_JWT_TOKEN);
        when(jwtUtil.extractUsername(VALID_JWT_TOKEN)).thenReturn(username);
        when(detailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(VALID_JWT_TOKEN, username)).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication()); // ← Aqui falhou
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticate_whenAuthenticationAlreadyExists() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_JWT_TOKEN);
        when(jwtUtil.extractUsername(VALID_JWT_TOKEN)).thenReturn("authenticatedUser");
        var existingAuth = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(existingAuth);

        filter.doFilterInternal(request, response, filterChain);

        assertSame(existingAuth, SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticate_whenTokenIsInvalid() throws Exception {
        String username = "unauthenticatedUser@email.com";
        UserDetails userDetails = new User(username, "password", List.of());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_JWT_TOKEN);
        when(jwtUtil.extractUsername(VALID_JWT_TOKEN)).thenReturn(username);
        when(detailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(VALID_JWT_TOKEN, username)).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldDoNothing_whenAuthorizationHeaderIsMissing() throws ServletException, IOException {

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldDoNothing_whenAuthorizationHeaderIsInvalid() throws ServletException, IOException {
        var mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "InvalidHeader");

        var mockHttpServletResponse = new MockHttpServletResponse();

        filter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, filterChain);

        verify(filterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldDoNothing_whenTokenUsernameIsNull() throws ServletException, IOException {
        var token = "Bearer some.invalid.token";

        var mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", token);
        var mockHttpServletResponse = new MockHttpServletResponse();

        filter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, filterChain);

        verify(filterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

}
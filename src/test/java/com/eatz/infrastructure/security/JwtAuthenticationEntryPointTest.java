package com.eatz.infrastructure.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntryPointTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private JwtAuthenticationEntryPoint entryPoint;

    private final StringWriter responseWriter = new StringWriter();

    @BeforeEach
    void setUp() throws IOException {
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getRequestURI()).thenReturn("/api/teste");
        when(authException.getMessage()).thenReturn("Token inválido");
    }

    @Test
    void shouldReturnUnauthorizedResponseWithCorrectJson() throws IOException, ServletException {
        entryPoint.commence(request, response, authException);

        verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
        verify(response).setContentType("application/json");

        String json = responseWriter.toString();

        assertTrue(json.contains("\"status\":401"));
        assertTrue(json.contains("\"error\":\"Acesso não autorizado\""));
        assertTrue(json.contains("\"message\":\"Token inválido\""));
        assertTrue(json.contains("\"path\":\"/api/teste\""));
    }

}
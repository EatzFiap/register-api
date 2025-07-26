package com.eatz.infrastructure.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAccessDeniedHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private JwtAccessDeniedHandler handler;

    private StringWriter responseWriter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getRequestURI()).thenReturn("/eatz-url");
    }


    @Test
    void handle_shouldReturnProperJsonResponseWithStatus403() throws Exception {
        AccessDeniedException ex = new AccessDeniedException("Acesso negado: falta permissão");

        handler.handle(request, response, ex);

        verify(response).setStatus(HttpStatus.FORBIDDEN.value());
        verify(response).setContentType("application/json");

        String jsonResponse = responseWriter.toString();

        assertNotNull(jsonResponse);
        assertThat(jsonResponse)
                .contains("\"status\":403")
                .contains("\"error\":\"Acesso negado\"")
                .contains("\"message\":\"Acesso negado: falta permissão\"")
                .contains("\"path\":\"/eatz-url\"");

        Map<String, Object> map = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        assertThat(map).containsKeys("status", "error", "message", "path");
    }

}
package com.proyecto.prueba.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.prueba.model.dto.ExceptionResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ExceptionResponseDTO errorResponse = new ExceptionResponseDTO(HttpStatus.UNAUTHORIZED.value(), "No autorizado: Se debe enviar un token valido para la autorización");

        ObjectMapper objectMapper = new ObjectMapper();
        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(errorResponseJson);
    }
}
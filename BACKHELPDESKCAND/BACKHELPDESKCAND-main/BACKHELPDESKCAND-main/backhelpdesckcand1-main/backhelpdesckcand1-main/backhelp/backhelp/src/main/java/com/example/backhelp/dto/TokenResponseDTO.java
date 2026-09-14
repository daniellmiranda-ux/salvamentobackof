package com.example.backhelp.dto;

public record TokenResponseDTO(
        String token,
        String tipo,
        UsuarioResponseDTO usuario
) {}
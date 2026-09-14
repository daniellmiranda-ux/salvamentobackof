package com.example.backhelp.dto;

import com.example.backhelp.model.Perfil;

public record UsuarioResponseDTO(
        Long id,
        String email,
        String setor,
        String cargo,
        Perfil perfil,
        boolean emailConfirmado
) {}
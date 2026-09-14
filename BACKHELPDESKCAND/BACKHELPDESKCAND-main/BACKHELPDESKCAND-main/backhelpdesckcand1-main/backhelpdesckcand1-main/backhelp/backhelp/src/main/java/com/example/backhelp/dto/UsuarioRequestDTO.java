package com.example.backhelp.dto;

import com.example.backhelp.model.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioRequestDTO(
        @NotBlank(message = "O e-mail é obrigatório")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@helpdeskcand\\.com$",
                message = "O e-mail deve ser do domínio @helpdeskcand.com"
        )
        String email,

        String senha,
        String setor,
        String cargo,
        Perfil perfil
) {}
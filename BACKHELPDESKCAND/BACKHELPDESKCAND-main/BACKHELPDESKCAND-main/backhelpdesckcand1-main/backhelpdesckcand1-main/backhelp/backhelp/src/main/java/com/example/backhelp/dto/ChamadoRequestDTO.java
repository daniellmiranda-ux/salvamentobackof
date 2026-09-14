package com.example.backhelp.dto;

import com.example.backhelp.model.Categoria;
import com.example.backhelp.model.Urgencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChamadoRequestDTO(

        @NotNull(message = "A categoria do chamado é obrigatória.")
        Categoria categoria,

        @NotNull(message = "A urgência do chamado é obrigatória.")
        Urgencia urgencia,

        @NotBlank(message = "A descrição do chamado não pode estar vazia.")
        String descricao,

        @Pattern(
                regexp = "^$|^.*\\.(pdf|svg|png|jpg|PDF|SVG|PNG|JPG)$",
                message = "Formato de anexo inválido. Permitidos apenas .pdf, .svg, .png ou .jpg"
        )
        String caminhoAnexo
) {}
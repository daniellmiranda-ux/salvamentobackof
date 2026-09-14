package com.example.backhelp.dto;

import com.example.backhelp.model.Categoria;
import com.example.backhelp.model.Perfil;
import com.example.backhelp.model.StatusChamado;
import com.example.backhelp.model.Urgencia;
import java.time.LocalDateTime;

public record ChamadoResponseDTO(
        Long id,
        String protocolo,
        Categoria categoria,
        Urgencia urgencia,
        String descricao,
        String caminhoAnexo,
        StatusChamado status,
        Perfil nivelAtendimento,
        LocalDateTime dataCriacao,
        LocalDateTime dataLimiteSla,
        String usuarioEmail,
        String atendenteEmail,
        String solucao
) {}
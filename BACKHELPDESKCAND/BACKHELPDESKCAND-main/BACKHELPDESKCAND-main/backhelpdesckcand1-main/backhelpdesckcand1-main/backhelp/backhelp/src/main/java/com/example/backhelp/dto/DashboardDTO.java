package com.example.backhelp.dto;

public record DashboardDTO(
        long totalAbertos,
        long totalResolvidos,
        long totalAtrasados,
        long totalHoje
) {}
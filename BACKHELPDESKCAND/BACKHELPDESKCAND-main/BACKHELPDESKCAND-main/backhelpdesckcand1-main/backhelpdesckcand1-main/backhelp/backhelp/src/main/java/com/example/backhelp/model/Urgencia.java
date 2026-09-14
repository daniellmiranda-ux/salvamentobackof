package com.example.backhelp.model;

public enum Urgencia {
    NORMAL(3),
    MEDIO(2),
    CRITICO(1);

    private final int horasSla;

    Urgencia(int horasSla) {
        this.horasSla = horasSla;
    }

    public int getHorasSla() {
        return horasSla;
    }
}
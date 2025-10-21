package com.raicesvivas.backend.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoEvento {
    PROXIMO("PRÓXIMO"),
    EN_CURSO("EN CURSO"),
    CANCELADO("CANCELADO"),
    FINALIZADO("FINALIZADO");

    private final String displayName;

    EstadoEvento(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
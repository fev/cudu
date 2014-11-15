package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Sexo {
    Masculino('M'),
    Femenino('F');

    private final char tipo;

    Sexo(char tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    private char getTipo() {
        return tipo;
    }
}

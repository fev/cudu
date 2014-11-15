package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoAsociado {
    Joven('J'),
    Kraal('K'),
    Comite('C');

    private final char tipo;

    TipoAsociado(char tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    private char getTipo() {
        return tipo;
    }
}
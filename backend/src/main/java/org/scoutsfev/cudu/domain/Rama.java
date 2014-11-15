package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Rama {
    Castores('C'),
    Lobatos('M'),
    Exploradores('E'),
    Pioneros('P'),
    Ruta('R');

    private final char tipo;

    Rama(char tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    private char getTipo() {
        return tipo;
    }
}

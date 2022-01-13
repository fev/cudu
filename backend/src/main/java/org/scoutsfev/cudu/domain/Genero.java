package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Genero {
    Masculino('M'),
    Femenino('F');

    private final char tipo;

    Genero(char tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    public char getTipo() {
        return tipo;
    }

    public static Genero parse(char valor) {
        for (Genero genero : Genero.values())
            if (genero.tipo == valor)
                return genero;
        throw new IllegalArgumentException("Valor incorrecto para Género: " + valor);
    }
}

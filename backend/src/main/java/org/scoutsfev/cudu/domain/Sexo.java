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
    public char getTipo() {
        return tipo;
    }

    public static Sexo parse(char valor) {
        for (Sexo sexo : Sexo.values())
            if (sexo.tipo == valor)
                return sexo;
        throw new IllegalArgumentException("Valor incorrecto para Sexo: " + valor);
    }
}

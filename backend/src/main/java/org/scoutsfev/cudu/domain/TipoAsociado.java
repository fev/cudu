package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoAsociado {
    Joven('J'),
    Kraal('K'),
    Comite('C'),
    Voluntario('V');

    private final char tipo;

    TipoAsociado(char tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    public char getTipo() {
        return tipo;
    }

    public static TipoAsociado parse(char valor) {
        for (TipoAsociado tipo : TipoAsociado.values())
            if (tipo.tipo == valor)
                return tipo;
        throw new IllegalArgumentException("Valor incorrecto para TipoAsociado: " + valor);
    }
}
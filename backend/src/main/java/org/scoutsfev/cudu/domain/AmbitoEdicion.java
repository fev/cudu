package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AmbitoEdicion {
    Personal('P'),
    Grupo('G'),
    Asociacion('A'),
    Escuela('E'),
    Federacion('F'),
    Seguro('S');

    private final char ambito;

    AmbitoEdicion(char ambito) {
        this.ambito = ambito;
    }

    @JsonValue
    public char getAmbito() {
        return ambito;
    }

    public static AmbitoEdicion parse(char valor) {
        for (AmbitoEdicion ambito : AmbitoEdicion.values())
            if (ambito.ambito == valor)
                return ambito;
        throw new IllegalArgumentException("Valor incorrecto para AmbitoEdicion: " + valor);
    }
}

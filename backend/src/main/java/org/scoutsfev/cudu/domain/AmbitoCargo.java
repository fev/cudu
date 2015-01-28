package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AmbitoCargo {
    Personal('P'),
    Kraal('K'),
    Comite('C'),
    Federativo('F');

    private char valor;

    AmbitoCargo(char valor) {
        this.valor = valor;
    }

    @JsonValue
    public char getAmbito() {
        return valor;
    }

    public static AmbitoCargo parse(Character valor) {
        for (AmbitoCargo ambito : AmbitoCargo.values())
            if (ambito.valor == valor)
                return ambito;
        throw new IllegalArgumentException("Valor incorrecto para AmbitoCargo: " + valor);
    }
}

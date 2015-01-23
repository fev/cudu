package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoActividad {
    Incognita('I'),
    Duda('D'),
    NoViene('N'),
    Viene('S'),
    Pagado('P'),
    Becado('B');

    private final char estado;

    EstadoActividad(char estado) {
        this.estado = estado;
    }

    @JsonValue
    public char getEstado() {
        return this.estado;
    }

    public static EstadoActividad parse(char valor) {
        for (EstadoActividad estado : EstadoActividad.values()) {
            if (estado.estado == valor)
                return estado;
        }
        throw new IllegalArgumentException("Valor incorrecto para EstadoActividad: " + valor);
    }
}

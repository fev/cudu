package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

public enum EstadoAsistente {
    Duda('D'),
    NoViene('N'),
    Viene('S'),
    Pagado('P'),
    Becado('B');

    private final char estado;

    EstadoAsistente(char estado) {
        this.estado = estado;
    }

    @JsonValue
    public char getEstado() {
        return this.estado;
    }

    public static EstadoAsistente parse(char valor) {
        return tryParse(valor).orElseThrow(() -> new IllegalArgumentException("Valor incorrecto para EstadoActividad: " + valor));
    }

    public static Optional<EstadoAsistente> tryParse(Character valor) {
        if (valor == null)
            return Optional.empty();
        for (EstadoAsistente estado : EstadoAsistente.values()) {
            if (estado.estado == valor)
                return Optional.of(estado);
        }
        return Optional.empty();
    }
}

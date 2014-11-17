package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Asociacion {
    SdA(0),
    SdC(1),
    MEV(3);

    private final int id;

    Asociacion(int id) {
        this.id = id;
    }

    @JsonValue
    public int getId() {
        return id;
    }

    public static Asociacion parse(int id) {
        for (Asociacion asociacion : Asociacion.values())
            if (asociacion.id == id)
                return asociacion;
        throw new IllegalArgumentException("Valor incorrecto para Asociacion: " + id);
    }
}
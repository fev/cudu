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
    private int getId() {
        return id;
    }
}
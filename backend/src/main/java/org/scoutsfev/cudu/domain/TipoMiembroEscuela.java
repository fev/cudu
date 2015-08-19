package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoMiembroEscuela {
    Formador(34),
    Colaborador(70),
    TutorDeFormacion(36);

    private final int cargoId;

    TipoMiembroEscuela(int cargoId) {
        this.cargoId = cargoId;
    }

    @JsonValue
    public int getCargoId() {
        return cargoId;
    }

    @JsonCreator
    public static TipoMiembroEscuela parse(int cargoId) {
        for (TipoMiembroEscuela miembroEscuela : TipoMiembroEscuela.values())
            if (miembroEscuela.cargoId == cargoId)
                return miembroEscuela;
        throw new IllegalArgumentException("Identificador de cargo incorrecto para TipoMiembroEscuela: " + cargoId);
    }
}

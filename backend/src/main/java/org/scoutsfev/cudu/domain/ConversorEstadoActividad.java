package org.scoutsfev.cudu.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEstadoActividad implements AttributeConverter<EstadoActividad, Character> {

    @Override
    public Character convertToDatabaseColumn(EstadoActividad estado) {
        return estado.getEstado();
    }

    @Override
    public EstadoActividad convertToEntityAttribute(Character estado) {
        if (estado == null)
            return null;
        return EstadoActividad.parse(estado);
    }
}



package org.scoutsfev.cudu.domain.conversores;

import org.scoutsfev.cudu.domain.EstadoAsistente;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEstadoAsistente implements AttributeConverter<EstadoAsistente, Character> {

    @Override
    public Character convertToDatabaseColumn(EstadoAsistente estado) {
        return estado.getEstado();
    }

    @Override
    public EstadoAsistente convertToEntityAttribute(Character estado) {
        if (estado == null)
            return null;
        return EstadoAsistente.parse(estado);
    }
}



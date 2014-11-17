package org.scoutsfev.cudu.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorAsociacion implements AttributeConverter<Asociacion, Integer>  {

    @Override
    public Integer convertToDatabaseColumn(Asociacion asociacion) {
        return asociacion.getId();
    }

    @Override
    public Asociacion convertToEntityAttribute(Integer valor) {
        if (valor == null)
            return null;
        return Asociacion.parse(valor);
    }
}

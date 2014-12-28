package org.scoutsfev.cudu.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorAmbitoEdicion implements AttributeConverter<AmbitoEdicion, Character> {

    @Override
    public Character convertToDatabaseColumn(AmbitoEdicion ambitoEdicion) {
        return ambitoEdicion.getAmbito();
    }

    @Override
    public AmbitoEdicion convertToEntityAttribute(Character ambito) {
        if (ambito == null)
            return null;
        return AmbitoEdicion.parse(ambito);
    }
}

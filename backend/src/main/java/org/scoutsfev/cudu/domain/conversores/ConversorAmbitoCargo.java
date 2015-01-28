package org.scoutsfev.cudu.domain.conversores;

import org.scoutsfev.cudu.domain.AmbitoCargo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorAmbitoCargo implements AttributeConverter<AmbitoCargo, Character> {

    @Override
    public Character convertToDatabaseColumn(AmbitoCargo ambitoCargo) {
        return ambitoCargo.getAmbito();
    }

    @Override
    public AmbitoCargo convertToEntityAttribute(Character ambito) {
        if (ambito == null)
            return null;
        return AmbitoCargo.parse(ambito);
    }
}

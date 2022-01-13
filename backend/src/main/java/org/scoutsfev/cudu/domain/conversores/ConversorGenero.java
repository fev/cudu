package org.scoutsfev.cudu.domain.conversores;

import org.scoutsfev.cudu.domain.Genero;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorGenero implements AttributeConverter<Genero, Character> {

    @Override
    public Character convertToDatabaseColumn(Genero genero) {
        if (genero == null)
            return null;
        return genero.getTipo();
    }

    @Override
    public Genero convertToEntityAttribute(Character valor) {
        if (valor == null)
            return null;
        return Genero.parse(valor);
    }
}

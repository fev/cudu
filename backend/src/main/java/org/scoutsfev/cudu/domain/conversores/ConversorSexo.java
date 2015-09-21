package org.scoutsfev.cudu.domain.conversores;

import org.scoutsfev.cudu.domain.Sexo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorSexo implements AttributeConverter<Sexo, Character> {

    @Override
    public Character convertToDatabaseColumn(Sexo sexo) {
        if (sexo == null)
            return null;
        return sexo.getTipo();
    }

    @Override
    public Sexo convertToEntityAttribute(Character valor) {
        if (valor == null)
            return null;
        return Sexo.parse(valor);
    }
}

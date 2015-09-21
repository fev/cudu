package org.scoutsfev.cudu.domain.conversores;

import org.scoutsfev.cudu.domain.TipoAsociado;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorTipoAsociado implements AttributeConverter<TipoAsociado, Character> {

    @Override
    public Character convertToDatabaseColumn(TipoAsociado tipoAsociado) {
        if (tipoAsociado == null)
            return null;
        return tipoAsociado.getTipo();
    }

    @Override
    public TipoAsociado convertToEntityAttribute(Character tipo) {
        if (tipo == null)
            return null;
        return TipoAsociado.parse(tipo);
    }
}

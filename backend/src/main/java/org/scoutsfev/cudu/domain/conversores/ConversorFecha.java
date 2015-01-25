package org.scoutsfev.cudu.domain.conversores;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

// Eliminar esta clase al migrar a spring-data-jpa 1.8
// https://jira.spring.io/browse/DATAJPA-650

@Converter(autoApply = true)
public class ConversorFecha implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        if (date == null)
            return null;
        final Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date value) {
        if (value == null)
            return null;
        // Night is dark and full of terrors and time zones
        final Instant instant = Instant.ofEpochMilli(value.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }
}
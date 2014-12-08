package org.scoutsfev.cudu.domain.validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ValidadorEdad implements ConstraintValidator<Edad, Date> {

    private Edad opts;

    @Override
    public void initialize(Edad opts) {
        this.opts = opts;
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        LocalDate fechaNacimiento = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long edad = fechaNacimiento.until(obtenerFechaActual(), ChronoUnit.YEARS);
        return edad >= opts.min() && edad <= opts.max();
    }

    // Únicamente para poder probar y no depender de fechas
    protected LocalDate obtenerFechaActual() {
        return LocalDate.now();
    }
}

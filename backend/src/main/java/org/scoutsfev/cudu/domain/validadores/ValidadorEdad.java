package org.scoutsfev.cudu.domain.validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ValidadorEdad implements ConstraintValidator<Edad, LocalDate> {

    private Edad opts;

    @Override
    public void initialize(Edad opts) {
        this.opts = opts;
    }

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        if (fechaNacimiento == null)
            return true;
        long edad = fechaNacimiento.until(obtenerFechaActual(), ChronoUnit.YEARS);
        return edad >= opts.min() && edad <= opts.max();
    }

    // Únicamente para poder probar y no depender de fechas
    protected LocalDate obtenerFechaActual() {
        return LocalDate.now();
    }
}

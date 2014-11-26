package org.scoutsfev.cudu.domain.validadores;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorTipo implements ConstraintValidator<ValidarTipo, Asociado> {

    @Override
    public void initialize(ValidarTipo constraintAnnotation) { }

    @Override
    public boolean isValid(Asociado asociado, ConstraintValidatorContext context) {
        if (asociado == null)
            return true;

        if (asociado.getTipo() == null)
            return false;

        if (asociado.getTipo() == TipoAsociado.Voluntario)
            return asociado.getGrupo() == null;

        return asociado.getGrupo() != null;
    }
}

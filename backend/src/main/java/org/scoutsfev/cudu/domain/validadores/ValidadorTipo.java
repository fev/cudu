package org.scoutsfev.cudu.domain.validadores;

import org.scoutsfev.cudu.domain.AmbitoEdicion;
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

        if (asociado.getTipo() == null || asociado.getAmbitoEdicion() == null)
            return false;

        boolean ambitoValido;
        if (asociado.getTipo() == TipoAsociado.Tecnico)
            ambitoValido = asociado.getAmbitoEdicion() != AmbitoEdicion.Grupo && asociado.getAmbitoEdicion() != AmbitoEdicion.Personal;
        else if (asociado.getTipo() == TipoAsociado.Voluntario)
            ambitoValido = asociado.getAmbitoEdicion() == AmbitoEdicion.Personal;
        else
            ambitoValido = asociado.getAmbitoEdicion() == AmbitoEdicion.Grupo || asociado.getAmbitoEdicion() == AmbitoEdicion.Personal;

        boolean grupoValido;
        if (asociado.getTipo() == TipoAsociado.Voluntario || asociado.getTipo() == TipoAsociado.Tecnico)
            grupoValido = asociado.getGrupo() == null;
        else
            grupoValido = asociado.getGrupo() != null;

        return ambitoValido && grupoValido;
    }
}

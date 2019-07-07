package org.scoutsfev.cudu.domain.validadores;

import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorTipo implements ConstraintValidator<ValidarTipo, Asociado> {

    public static final String TIPO_NULO = "{ValidarTipo.TipoNulo}";
    public static final String TIPO_EMAIL_NO_NULO = "{ValidarTipo.TipoEmailNoNulo}";
    public static final String AMBITO_NULO = "{ValidarTipo.AmbitoNulo}";
    public static final String AMBITO_NO_VALIDO = "{ValidarTipo.AmbitoNoValido}";
    public static final String DEBE_TENER_GRUPO = "{ValidarTipo.DebeTenerGrupo}";
    public static final String NO_DEBE_TENER_GRUPO = "{ValidarTipo.NoDebeTenerGrupo}";

    @Override
    public void initialize(ValidarTipo constraintAnnotation) { }

    @Override
    public boolean isValid(Asociado asociado, ConstraintValidatorContext context) {
        if (asociado == null)
            return true;

        context.disableDefaultConstraintViolation();

        if (asociado.getTipo() == null) {
            context.buildConstraintViolationWithTemplate(TIPO_NULO).addConstraintViolation();
            return false;
        }

        if (asociado.getTipo() != TipoAsociado.Joven && asociado.getEmailContacto() == null) {
            context.buildConstraintViolationWithTemplate(TIPO_EMAIL_NO_NULO).addConstraintViolation();
            return false;
        }

        if (asociado.getAmbitoEdicion() == null) {
            context.buildConstraintViolationWithTemplate(AMBITO_NULO).addConstraintViolation();
            return false;
        }

        boolean ambitoValido;
        if (asociado.getTipo() == TipoAsociado.Tecnico)
            ambitoValido = asociado.getAmbitoEdicion() != AmbitoEdicion.Grupo && asociado.getAmbitoEdicion() != AmbitoEdicion.Personal;
        else if (asociado.getTipo() == TipoAsociado.Voluntario)
            ambitoValido = asociado.getAmbitoEdicion() == AmbitoEdicion.Personal;
        else
            ambitoValido = asociado.getAmbitoEdicion() == AmbitoEdicion.Grupo || asociado.getAmbitoEdicion() == AmbitoEdicion.Personal;

        if (!ambitoValido)
            context.buildConstraintViolationWithTemplate(AMBITO_NO_VALIDO).addConstraintViolation();

        boolean grupoValido;
        if (asociado.getTipo() == TipoAsociado.Voluntario || asociado.getTipo() == TipoAsociado.Tecnico) {
            grupoValido = asociado.getGrupoId() == null;
            if (!grupoValido)
                context.buildConstraintViolationWithTemplate(NO_DEBE_TENER_GRUPO).addConstraintViolation();
        } else {
            grupoValido = asociado.getGrupoId() != null;
            if (!grupoValido)
                context.buildConstraintViolationWithTemplate(DEBE_TENER_GRUPO).addConstraintViolation();
        }

        return ambitoValido && grupoValido;
    }
}

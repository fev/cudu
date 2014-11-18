package org.scoutsfev.cudu.domain.validadores;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorRama implements ConstraintValidator<ValidarRama, Asociado> {

    @Override
    public void initialize(ValidarRama constraintAnnotation) { }

    @Override
    public boolean isValid(Asociado asociado, ConstraintValidatorContext context) {
        if (asociado == null || asociado.getTipo() == null)
            return true;

        int noRamas = 0;
        noRamas = asociado.isRamaCastores() ? ++noRamas : noRamas;
        noRamas = asociado.isRamaLobatos() ? ++noRamas : noRamas;
        noRamas = asociado.isRamaExploradores() ? ++noRamas : noRamas;
        noRamas = asociado.isRamaPioneros() ? ++noRamas : noRamas;
        noRamas = asociado.isRamaRuta() ? ++noRamas : noRamas;
        return asociado.getTipo() != TipoAsociado.Joven || noRamas == 1;
    }
}

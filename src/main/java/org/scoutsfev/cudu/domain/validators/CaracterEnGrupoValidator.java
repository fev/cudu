package org.scoutsfev.cudu.domain.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validador para comprobar que un caracter esté dentro de un grupo de
 * carácteres especificado.
 */
public class CaracterEnGrupoValidator implements
		ConstraintValidator<CaracterEnGrupo, Character> {

	private char[] grupo;

	@Override
	public void initialize(CaracterEnGrupo constraintAnnotation) {
		this.grupo = constraintAnnotation.grupo();
	}

	@Override
	public boolean isValid(Character value, ConstraintValidatorContext context) {
		if (value.charValue() == Character.MIN_VALUE)
			return false;

		for (char c : grupo) {
			if (value.charValue() == c)
				return true;
		}

		return false;
	}

}

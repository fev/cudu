package org.scoutsfev.cudu.domain.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

/**
 * Comprueba que un caracter esté dentro de un grupo de carácteres especificado.
 * @see org.scoutsfev.cudu.domain.validators.CaracterEnGrupoValidator.
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CaracterEnGrupoValidator.class)
@Documented
@NotNull
public @interface CaracterEnGrupo {
	char[] grupo();

	String message() default "CaracterEnGrupo";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

package org.scoutsfev.cudu.domain.validadores;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidadorDniNie.class)
@Documented
public @interface ValidarDniNie {

    String message() default "{org.scoutsfev.cudu.domain.validadores.DniNie.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package org.scoutsfev.cudu.domain.validadores;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidadorDniNie.class)
@Documented
public @interface DniNie {

    String message() default "{org.scoutsfev.cudu.domain.validadores.Nif.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

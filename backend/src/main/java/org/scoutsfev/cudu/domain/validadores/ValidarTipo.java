package org.scoutsfev.cudu.domain.validadores;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidadorTipo.class})
public @interface ValidarTipo {

    String message() default "{org.scoutsfev.cudu.domain.validadores.ValidarTipo.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package org.scoutsfev.cudu.domain.validadores;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidadorRama.class})
public @interface ValidarRama {

    String message() default "{ValidarRama.JovenSoloEnUnaRama}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

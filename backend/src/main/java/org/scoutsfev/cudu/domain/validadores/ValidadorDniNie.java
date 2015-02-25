package org.scoutsfev.cudu.domain.validadores;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorDniNie implements ConstraintValidator<DniNie, String> {

    public static final String DIGITO_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public void initialize(DniNie constraintAnnotation) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNullOrEmpty(value))
            return true;

        String dni = CharMatcher.JAVA_LETTER_OR_DIGIT.retainFrom(value).toUpperCase();
        if (dni.length() < 2)
            return false;

        if (CharMatcher.anyOf("KLMXYZ").matches(dni.charAt(0))) {
            return CharMatcher.JAVA_DIGIT.matchesAllOf(dni.substring(1, dni.length()));
        }

        Integer valorNumerico = Ints.tryParse(dni.substring(0, dni.length() - 1));
        if (valorNumerico == null)
            return false;
        char digitoControl = DIGITO_CONTROL.charAt(valorNumerico % 23);
        return digitoControl == dni.charAt(dni.length() - 1);
    }
}

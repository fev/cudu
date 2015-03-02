package org.scoutsfev.cudu.domain.validadores;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidadorDniNieTest {

    private ValidadorDniNie validador;
    private ConstraintValidatorContext context;
    private Asociado asociado;

    @Before
    public void setUp() throws Exception {
        asociado = GeneradorDatosDePrueba.generarAsociado();
        validador = new ValidadorDniNie();
        context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        HibernateConstraintValidatorContext nhContext = mock(HibernateConstraintValidatorContext.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(nhContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(context.unwrap(HibernateConstraintValidatorContext.class)).thenReturn(nhContext);
    }

    @Test
    public void si_la_fecha_de_nacimiento_del_asociado_es_nula_no_valida_el_dni() throws Exception {
        asociado.setDni("AAA");
        asociado.setFechaNacimiento(null);
        assertTrue(validador.isValid(asociado, context));
    }

    @Test
    public void el_dni_es_requerido_cuando_el_asociado_es_mayor_de_18_años() throws Exception {
        asociado.setDni(null);
        asociado.setFechaNacimiento(LocalDate.now().minus(30, ChronoUnit.YEARS));
        assertFalse(validador.isValid(asociado, context));
        asociado.setDni("");
        assertFalse(validador.isValid(asociado, context));
    }

    @Test
    public void el_dni_puede_ser_nulo_cuando_el_asociado_es_menor_de_18_años() throws Exception {
        asociado.setDni(null);
        asociado.setFechaNacimiento(LocalDate.now().minus(10, ChronoUnit.YEARS));
        assertTrue(validador.isValid(asociado, context));
        asociado.setDni("");
        assertTrue(validador.isValid(asociado, context));
    }

    @Test
    public void no_valida_cuando_el_nif_esta_blanco() throws Exception {
        noValida(" ");
        noValida("  ");
    }

    @Test
    public void no_valida_dnis_de_una_letra_pero_si_de_dos() throws Exception {
        noValida("7");
        noValida("F");
        noValida("K");
        valida("7F");
        valida("K5");
    }

    @Test
    public void valida_cuando_el_nif_es_extrangero() throws Exception {
        valida("K12345678");
        valida("L12345678");
        valida("M12345678");
        valida("X12345678");
        valida("Y12345678");
        valida("Z12345678");
    }

    @Test
    public void no_valida_cuando_el_nif_es_extrangero_y_el_digito_inicial_es_incorrecto() throws Exception {
        noValida("A12345678");
        noValida("012345678");
        noValida("01234567H");
        noValida("A1234567H");
    }

    @Test
    public void no_valida_cuando_el_nif_es_extrangero_el_digito_inicial_es_correcto_pero_el_resto_de_caracteres_no_son_numeros() throws Exception {
        noValida("K1234567H");
        noValida("K123Y4567");
    }

    @Test
    public void valida_dnis_correctos() throws Exception {
        valida("12345678Z");
        valida("1234567L");
    }

    @Test
    public void valida_dnis_correctos_en_minusculas() throws Exception {
        valida("12345678z");
        valida("1234567l");
    }

    @Test
    public void valida_nifs_extrangeros_en_minusculas() throws Exception {
        valida("k12345678");
        valida("l12345678");
        valida("m12345678");
        valida("x12345678");
        valida("y12345678");
        valida("z12345678");
    }

    @Test
    public void no_valida_dnis_mal_formados() throws Exception {
        noValida("R2345678Z");
        noValida("1R2345678Z");
    }

    @Test
    public void no_valida_dnis_con_digito_de_control_incorrecto() throws Exception {
        noValida("12345678A");
        noValida("1234567A");
    }

    @Test
    public void valida_dnis_con_caracteres_extraños() throws Exception {
        valida("K-12345678");
        valida("K-123/45678");
        valida("K 12345678");
        valida("K.12345678");
        valida("12345678-Z");
        valida("12345678 Z");
        valida("1234/5678 Z");
        valida("12345678.Z");
    }

    private void valida(String nif) {
        assertTrue(ValidadorDniNie.validarDniNie(nif, context));
    }

    private void noValida(String nif) {
        assertFalse(ValidadorDniNie.validarDniNie(nif, context));
    }
}

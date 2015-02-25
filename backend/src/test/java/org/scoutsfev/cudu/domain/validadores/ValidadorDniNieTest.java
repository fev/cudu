package org.scoutsfev.cudu.domain.validadores;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidadorDniNieTest {

    private ValidadorDniNie validador;

    @Before
    public void setUp() throws Exception {
        validador = new ValidadorDniNie();
    }

    @Test
    public void valida_cuando_el_nif_es_nulo_o_vacio() throws Exception {
        valida(null);
        valida("");
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
        assertTrue(validador.isValid(nif, null));
    }

    private void noValida(String nif) {
        assertFalse(validador.isValid(nif, null));
    }
}

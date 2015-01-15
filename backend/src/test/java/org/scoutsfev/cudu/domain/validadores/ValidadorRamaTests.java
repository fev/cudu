package org.scoutsfev.cudu.domain.validadores;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;

public class ValidadorRamaTests {

    private ValidadorRama validador;
    private Asociado asociado;

    @Before
    public void setUp() throws Exception {
        validador = new ValidadorRama();
        asociado = GeneradorDatosDePrueba.generarAsociado();
        asociado.setRamaManada(false);
        assumeThat(asociado.getTipo(), is(equalTo(TipoAsociado.Joven)));
        assumeFalse(asociado.isRamaColonia());
        assumeFalse(asociado.isRamaManada());
        assumeFalse(asociado.isRamaExploradores());
        assumeFalse(asociado.isRamaExpedicion());
        assumeFalse(asociado.isRamaRuta());
    }

    @Test
    public void si_el_asociado_es_nulo_la_entidad_no_se_ha_construido_todavia_y_el_validador_pasa() throws Exception {
        assertTrue(validador.isValid(null, null));
    }

    @Test
    public void si_el_tipo_del_asociado_es_nulo_la_entidad_no_se_ha_construido_todavia_y_el_validador_pasa() throws Exception {
        asociado.setTipo(null);
        assertTrue(validador.isValid(asociado, null));
    }

    @Test
    public void un_joven_no_puede_estar_en_mas_de_una_rama() throws Exception {
        asociado.setRamaColonia(true);
        asociado.setRamaManada(true);
        assertFalse(validador.isValid(asociado, null));
    }

    @Test
    public void un_joven_debe_estar_obligatoriamente_en_una_rama() throws Exception {
        assertFalse(validador.isValid(asociado, null));
        asociado.setRamaManada(true);
        assertTrue(validador.isValid(asociado, null));
    }

    @Test
    public void los_asociados_que_no_son_jovenes_pueden_no_estar_en_alguna_rama() throws Exception {
        for (TipoAsociado tipo : TipoAsociado.values()) {
            if (tipo == TipoAsociado.Joven)
                continue;
            asociado.setTipo(tipo);
            assertTrue(validador.isValid(asociado, null));
        }
    }

    @Test
    public void los_asociados_que_no_son_jovenes_pueden_estar_en_multiples_ramas() throws Exception {
        asociado.setRamaColonia(true);
        asociado.setRamaManada(true);
        for (TipoAsociado tipo : TipoAsociado.values()) {
            if (tipo == TipoAsociado.Joven)
                continue;
            asociado.setTipo(tipo);
            assertTrue(validador.isValid(asociado, null));
        }
    }
}

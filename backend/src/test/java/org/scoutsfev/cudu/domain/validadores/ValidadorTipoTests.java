package org.scoutsfev.cudu.domain.validadores;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidadorTipoTests {

    private ValidadorTipo validador;

    @Before
    public void setUp() throws Exception {
        validador = new ValidadorTipo();
    }

    @Test
    public void cuando_el_asociado_es_nulo_el_validador_pasa() throws Exception {
        assertTrue(validador.isValid(null, null));
    }

    @Test
    public void el_tipo_no_puede_ser_nulo() throws Exception {
        Asociado asociado = GeneradorDatosDePrueba.generarAsociado();
        asociado.setTipo(null);
        assertFalse(validador.isValid(asociado, null));
    }

    @Test
    public void cuando_el_asociado_no_es_de_tipo_voluntario_debe_pertenecer_a_un_grupo() throws Exception {
        TipoAsociado tipo = TipoAsociado.Joven;
        Grupo grupo = GeneradorDatosDePrueba.generarGrupo(Optional.<String>empty());
        Asociado asociado = GeneradorDatosDePrueba.generarAsociado(grupo);
        asociado.setTipo(tipo);
        asociado.setGrupo(grupo);
        assertTrue(validador.isValid(asociado, null));
    }

    @Test
    public void es_invalido_cuando_el_asociado_no_es_voluntario_y_no_pertenece_a_un_grupo() throws Exception {
        TipoAsociado tipo = TipoAsociado.Joven;
        Asociado asociado = GeneradorDatosDePrueba.generarAsociado();
        asociado.setTipo(tipo);
        asociado.setGrupo(null);
        assertFalse(validador.isValid(asociado, null));
    }

    @Test
    public void es_valido_cuando_el_asociado_es_voluntario_y_no_tiene_grupo() throws Exception {
        Asociado asociado = GeneradorDatosDePrueba.generarAsociado();
        asociado.setTipo(TipoAsociado.Voluntario);
        asociado.setGrupo(null);
        assertTrue(validador.isValid(asociado, null));
    }

    @Test
    public void es_invalido_cuando_el_asociado_es_voluntario_y_tiene_grupo() throws Exception {
        Grupo grupo = GeneradorDatosDePrueba.generarGrupo(Optional.<String>empty());
        Asociado asociado = GeneradorDatosDePrueba.generarAsociado(grupo);
        asociado.setTipo(TipoAsociado.Voluntario);
        asociado.setGrupo(grupo);
        assertFalse(validador.isValid(asociado, null));
    }
}

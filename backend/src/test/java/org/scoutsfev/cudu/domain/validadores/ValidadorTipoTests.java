package org.scoutsfev.cudu.domain.validadores;

import com.google.common.collect.FluentIterable;
import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidadorTipoTests {

    private ValidadorTipo validador;
    private Asociado asociado;
    private Grupo grupo;
    private ConstraintValidatorContext contexto;

    @Before
    public void setUp() throws Exception {
        ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        contexto = mock(ConstraintValidatorContext.class);
        when(contexto.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        validador = new ValidadorTipo();
        grupo = GeneradorDatosDePrueba.generarGrupo(Optional.<String>empty());
        asociado = GeneradorDatosDePrueba.generarAsociado();
        asociado.setGrupo(null);
    }

    @Test
    public void cuando_el_asociado_es_nulo_el_validador_pasa() throws Exception {
        assertTrue(validador.isValid(null, contexto));
    }

    @Test
    public void el_tipo_no_puede_ser_nulo() throws Exception {
        asociado.setTipo(null);
        assertFalse(validador.isValid(asociado, contexto));
    }

    @Test
    public void el_ambito_de_edicion_no_puede_ser_nulo() throws Exception {
        asociado.setTipo(TipoAsociado.Voluntario);
        asociado.setAmbitoEdicion(null);
        assertFalse(validador.isValid(asociado, contexto));
    }

    @Test
    public void si_el_asociado_es_joven_kraal_o_comite_su_ambito_de_edicion_es_el_grupo_o_personal() throws Exception {
        valido(grupo, TipoAsociado.Joven, AmbitoEdicion.Grupo, AmbitoEdicion.Personal);
        valido(grupo, TipoAsociado.Kraal, AmbitoEdicion.Grupo, AmbitoEdicion.Personal);
        valido(grupo, TipoAsociado.Comite, AmbitoEdicion.Grupo, AmbitoEdicion.Personal);
        noValido(grupo, TipoAsociado.Joven, AmbitoEdicion.Asociacion, AmbitoEdicion.Federacion, AmbitoEdicion.Escuela, AmbitoEdicion.Seguro);
    }

    @Test
    public void si_el_asociado_es_tecnico_su_ambito_de_edicion_es_asociacion_federacion_lluerna_o_seguro() throws Exception {
        valido(null, TipoAsociado.Tecnico, AmbitoEdicion.Asociacion, AmbitoEdicion.Federacion, AmbitoEdicion.Escuela, AmbitoEdicion.Seguro);
        noValido(null, TipoAsociado.Tecnico, AmbitoEdicion.Grupo, AmbitoEdicion.Personal);
    }

    @Test
    public void si_el_asociado_es_voluntario_su_ambito_de_edicion_es_personal() throws Exception {
        valido(null, TipoAsociado.Voluntario, AmbitoEdicion.Personal);
        noValido(null, TipoAsociado.Voluntario, FluentIterable.of(AmbitoEdicion.values()).filter(a -> a != AmbitoEdicion.Personal).toArray(AmbitoEdicion.class));
    }

    @Test
    public void si_el_asociado_es_tecnico_no_tiene_grupo() throws Exception {
        valido(null, TipoAsociado.Tecnico, AmbitoEdicion.Asociacion);
        noValido(grupo, TipoAsociado.Tecnico, AmbitoEdicion.Asociacion);
    }

    @Test
    public void si_el_asociado_es_voluntario_no_tiene_grupo() throws Exception {
        valido(null, TipoAsociado.Voluntario, AmbitoEdicion.Personal);
        noValido(grupo, TipoAsociado.Voluntario, AmbitoEdicion.Personal);
    }

    @Test
    public void si_el_asociado_no_es_tecnico_o_voluntario_debe_de_tener_grupo() throws Exception {
        for (TipoAsociado tipo : FluentIterable.of(TipoAsociado.values()).filter(t -> t != TipoAsociado.Tecnico && t != TipoAsociado.Voluntario)) {
            valido(grupo, tipo, AmbitoEdicion.Grupo, AmbitoEdicion.Personal);
            noValido(null, tipo, AmbitoEdicion.Grupo, AmbitoEdicion.Personal);
        }
    }

    private void valido(Grupo grupo, TipoAsociado tipo, AmbitoEdicion ... ambitos) {
        String grupoNulo = grupo == null ? "nulo" : "no nulo";
        for (AmbitoEdicion ambito : ambitos) {
            asociado.setGrupo(grupo);
            asociado.setTipo(tipo);
            asociado.setAmbitoEdicion(ambito);
            assertTrue("El validador debería pasar con grupo " + grupoNulo + ", tipo '" + tipo + "' y Ambito '" + ambito + "'.", validador.isValid(asociado, contexto));
        }
    }

    private void noValido(Grupo grupo, TipoAsociado tipo, AmbitoEdicion ... ambitos) {
        String grupoNulo = grupo == null ? "nulo" : "no nulo";
        for (AmbitoEdicion ambito : ambitos) {
            asociado.setGrupo(grupo);
            asociado.setTipo(tipo);
            asociado.setAmbitoEdicion(ambito);
            assertFalse("El validador no debería pasar con grupo " + grupoNulo + ", tipo '" + tipo + "' y Ambito '" + ambito + "'.", validador.isValid(asociado, contexto));
        }
    }
}

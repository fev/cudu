package org.scoutsfev.cudu.services;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationServiceLluernaTests {

    private AuthorizationService service;
    private Usuario usuarioConAcceso;

    @Before
    public void setUp() throws Exception {
        AsociadoRepository repository = mock(AsociadoRepository.class);
        service = new AuthorizationService(repository);
        usuarioConAcceso = mock(Usuario.class);
        when(usuarioConAcceso.isUsuarioActivo()).thenReturn(true);
        when(usuarioConAcceso.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuarioConAcceso.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Escuela);
        assertTrue(service.puedeAccederLluerna(usuarioConAcceso));
    }

    @Test
    public void no_accede_cuando_el_usuario_es_nulo() throws Exception {
        assertFalse(service.puedeAccederLluerna(null));
    }

    @Test
    public void no_accede_cuando_el_usuario_no_esta_activo() throws Exception {
        assertFalse(service.puedeAccederLluerna(null));
    }

    @Test
    public void no_accede_cuando_el_usuario_no_tiene_tipo() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getTipo()).thenReturn(null);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Escuela);
        assertFalse(service.puedeAccederLluerna(usuario));
    }

    @Test
    public void no_accede_cuando_el_usuario_no_tiene_ambito() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(null);
        assertFalse(service.puedeAccederLluerna(usuario));
    }

    @Test
    public void no_accede_cuando_el_usuario_es_de_cualquier_tipo_distinto_del_de_tecnico() {
        for (TipoAsociado tipoAsociado : TipoAsociado.values()) {
            if (tipoAsociado != TipoAsociado.Tecnico)
            {
                when(usuarioConAcceso.getTipo()).thenReturn(tipoAsociado);
                assertFalse("Con acceso incorrecto: " + tipoAsociado.toString(), service.puedeAccederLluerna(usuarioConAcceso));
            }
        }
    }

    @Test
    public void no_accede_cuando_el_usuario_es_de_cualquier_ambito_distinto_del_de_lluerna_o_fev() {
        for (AmbitoEdicion ambitoEdicion: AmbitoEdicion.values()) {
            if (ambitoEdicion != AmbitoEdicion.Escuela && ambitoEdicion != AmbitoEdicion.Federacion)
            {
                when(usuarioConAcceso.getAmbitoEdicion()).thenReturn(ambitoEdicion);
                assertFalse("Con acceso incorrecto: " + ambitoEdicion.toString(), service.puedeAccederLluerna(usuarioConAcceso));
            }
        }
    }

    @Test
    public void puede_acceder_cuando_el_usuario_es_de_ambito_de_escuela_y_no_tiene_restricciones_de_asociacion() throws Exception {
        assertTrue(service.puedeAccederLluerna(usuarioConAcceso));
    }

    @Test
    public void puede_acceder_cuando_el_usuario_es_de_ambito_federativo_y_no_tiene_restricciones_de_asociacion() throws Exception {
        when(usuarioConAcceso.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        Restricciones restricciones = new Restricciones();
        restricciones.setRestriccionAsociacion(null);
        when(usuarioConAcceso.getRestricciones()).thenReturn(restricciones);
        assertTrue(service.puedeAccederLluerna(usuarioConAcceso));
    }

    @Test
    public void no_accede_cuando_el_usuario_es_de_ambito_federativo_pero_tiene_restricciones_de_asociacion() {
        when(usuarioConAcceso.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        Restricciones restricciones = new Restricciones();
        restricciones.setRestriccionAsociacion(Asociacion.SdA);
        when(usuarioConAcceso.getRestricciones()).thenReturn(restricciones);
        assertFalse(service.puedeAccederLluerna(usuarioConAcceso));
    }

    @Test
    public void no_accede_cuando_el_usuario_es_de_ambito_federativo_pero_sus_restricciones_no_pueden_comprobarse() {
        when(usuarioConAcceso.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        when(usuarioConAcceso.getRestricciones()).thenReturn(null);
        assertFalse(service.puedeAccederLluerna(usuarioConAcceso));
    }

    @Test
    public void no_accede_cuando_el_usuario_no_tiene_ni_tipo_ni_ambito() throws Exception {
        assertFalse(service.puedeAccederLluerna(null));
    }
}

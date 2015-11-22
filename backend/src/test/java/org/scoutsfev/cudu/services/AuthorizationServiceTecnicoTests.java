package org.scoutsfev.cudu.services;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.AsociadoStorage;
import org.scoutsfev.cudu.storage.GrupoRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTecnicoTests {

    private AuthorizationService service;
    private Usuario usuarioConAcceso;

    @Before
    public void setUp() throws Exception {
        AsociadoStorage asociadoStorage = mock(AsociadoStorage.class);
        GrupoRepository grupoRepository = mock(GrupoRepository.class);
        service = new AuthorizationService(asociadoStorage, grupoRepository);
        usuarioConAcceso = mock(Usuario.class);
        when(usuarioConAcceso.isUsuarioActivo()).thenReturn(true);
        when(usuarioConAcceso.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuarioConAcceso.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        assertTrue(service.esTecnico(usuarioConAcceso));
    }

    @Test
    public void no_es_tecnico_cuando_el_usuario_es_nulo() throws Exception {
        assertFalse(service.esTecnico(null));
    }

    @Test
    public void no_es_tecnico_cuando_el_usuario_no_esta_activo() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.isUsuarioActivo()).thenReturn(false);
        assertFalse(service.esTecnico(usuario));
    }

    @Test
    public void no_es_tecnico_cuando_el_usuario_no_tiene_tipo() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getTipo()).thenReturn(null);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Escuela);
        assertFalse(service.esTecnico(usuario));
    }

    @Test
    public void no_es_tecnico_cuando_el_usuario_no_tiene_ambito() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(null);
        assertFalse(service.esTecnico(usuario));
    }

    @Test
    public void no_es_tecnico_cuando_el_usuario_es_de_cualquier_tipo_distinto_del_de_tecnico() {
        for (TipoAsociado tipoAsociado : TipoAsociado.values()) {
            if (tipoAsociado != TipoAsociado.Tecnico)
            {
                when(usuarioConAcceso.getTipo()).thenReturn(tipoAsociado);
                assertFalse("Con acceso incorrecto: " + tipoAsociado.toString(), service.esTecnico(usuarioConAcceso));
            }
        }
    }

    @Test
    public void no_es_tecnico_cuando_el_usuario_es_de_cualquier_ambito_distinto_del_de_asociacion_o_fev() {
        for (AmbitoEdicion ambitoEdicion: AmbitoEdicion.values()) {
            if (ambitoEdicion != AmbitoEdicion.Asociacion && ambitoEdicion != AmbitoEdicion.Federacion)
            {
                when(usuarioConAcceso.getAmbitoEdicion()).thenReturn(ambitoEdicion);
                assertFalse("Con acceso incorrecto: " + ambitoEdicion.toString(), service.esTecnico(usuarioConAcceso));
            }
        }
    }

    @Test
    public void es_tecnico_cuando_el_tipo_es_tecnico_y_el_ambito_asociativo() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.isUsuarioActivo()).thenReturn(true);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Asociacion);
        assertTrue(service.esTecnico(usuario));
    }

    @Test
    public void es_tecnico_cuando_el_tipo_es_tecnico_y_el_ambito_federativo() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.isUsuarioActivo()).thenReturn(true);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        assertTrue(service.esTecnico(usuario));
    }

    @Test
    public void no_es_tecnico_cuando_el_usuario_no_tiene_ni_tipo_ni_ambito() throws Exception {
        Usuario usuario = mock(Usuario.class);
        when(usuario.isUsuarioActivo()).thenReturn(true);
        when(usuario.getTipo()).thenReturn(null);
        when(usuario.getAmbitoEdicion()).thenReturn(null);
        assertFalse(service.esTecnico(usuario));
    }
}

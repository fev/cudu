package org.scoutsfev.cudu.services;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.GrupoRepository;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationServiceEdicionUsuariosGrupoTests {

    private static final String grupoId = "UP";

    private AsociadoRepository asociadoRepository;
    private GrupoRepository grupoRepository;
    private AuthorizationService service;

    @Before
    public void setUp() throws Exception {
        asociadoRepository = mock(AsociadoRepository.class);
        grupoRepository = mock(GrupoRepository.class);
        service = new AuthorizationService(asociadoRepository, grupoRepository);
    }

    @Test
    public void no_permite_si_el_usuario_es_nulo() throws Exception {
        assertFalse(service.puedeEditarUsuariosDelGrupo("UP", null));
    }

    @Test
    public void no_permite_si_el_identificador_del_grupo_es_nulo() throws Exception {
        assertFalse(service.puedeEditarUsuariosDelGrupo(null, mock(Usuario.class)));
    }

    @Test
    public void no_permite_si_el_identificador_del_grupo_y_el_usuario_son_nulos() throws Exception {
        assertFalse(service.puedeEditarUsuariosDelGrupo(null, null));
    }

    @Test
    public void no_permite_si_el_usuario_no_esta_activo() throws Exception {
        Usuario usuario = usuarioDeGrupoConPermisos();
        when(usuario.isUsuarioActivo()).thenReturn(false);
        assertFalse(service.puedeEditarUsuariosDelGrupo(grupoId, null));
    }

    @Test
    public void permite_si_el_usuario_es_tecnico_fev_y_no_tiene_restricciones_de_asociacion() throws Exception {
        Usuario fev = tecnicoFev();
        when(fev.getRestricciones()).thenReturn(new Restricciones());
        assertTrue(service.puedeEditarUsuariosDelGrupo(grupoId, fev));
    }

    @Test
    public void no_permite_si_el_usuario_es_tecnico_asociativo_y_no_encuentra_el_grupo() throws Exception {
        Usuario usuario = tecnicoFev();
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("AK");
        when(grupo.getAsociacion()).thenReturn(Asociacion.MEV);
        when(grupoRepository.findOne(anyString())).thenReturn(null);
        Restricciones restricciones = new Restricciones();
        restricciones.setRestriccionAsociacion(Asociacion.MEV);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        assertFalse(service.puedeEditarUsuariosDelGrupo("AK", usuario));
    }

    @Test
    public void permite_si_el_usuario_es_tecnico_de_asociacion_y_el_grupo_es_de_la_misma_asociacion() throws Exception {
        Usuario usuario = tecnicoFev();
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("AK");
        when(grupo.getAsociacion()).thenReturn(Asociacion.MEV);
        when(grupoRepository.findOne(argThat(is(equalTo(grupo.getId()))))).thenReturn(grupo);
        when(grupoRepository.findOne(argThat(is(not(equalTo(grupo.getId())))))).thenReturn(null);
        Restricciones restricciones = new Restricciones();
        restricciones.setRestriccionAsociacion(Asociacion.MEV);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        assertTrue(service.puedeEditarUsuariosDelGrupo("AK", usuario));
    }

    @Test
    public void no_permite_si_el_usuario_es_tecnico_de_asociacion_y_el_grupo_es_de_otra_asociacion() throws Exception {
        Usuario usuario = tecnicoFev();
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("AK");
        when(grupo.getAsociacion()).thenReturn(Asociacion.SdA);
        when(grupoRepository.findOne(argThat(is(equalTo(grupo.getId()))))).thenReturn(grupo);
        when(grupoRepository.findOne(argThat(is(not(equalTo(grupo.getId())))))).thenReturn(null);
        Restricciones restricciones = new Restricciones();
        restricciones.setRestriccionAsociacion(Asociacion.SdC);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        assertFalse(service.puedeEditarUsuariosDelGrupo("AK", usuario));
    }

    @Test
    public void no_permite_si_el_usuario_es_valido_pero_de_tipo_joven() throws Exception {
        Usuario usuario = usuarioDeGrupoConPermisos();
        when(usuario.getTipo()).thenReturn(TipoAsociado.Joven);
        assertFalse(service.puedeEditarUsuariosDelGrupo(grupoId, usuario));
    }

    @Test
    public void no_permite_si_el_usuario_de_grupo_es_valido_pero_por_alguna_razon_no_tiene_grupo() throws Exception {
        Usuario usuario = usuarioDeGrupoConPermisos();
        when(usuario.getGrupo()).thenReturn(null);
        assertFalse(service.puedeEditarUsuariosDelGrupo(grupoId, usuario));
    }

    @Test
    public void no_permite_si_el_usuario_de_grupo_es_valido_pero_el_grupo_es_diferente() throws Exception {
        Usuario usuario = usuarioDeGrupoConPermisos();
        assertNotEquals(grupoId, "AK", "Este test asume que los grupos son distintos");
        assertFalse(service.puedeEditarUsuariosDelGrupo("AK", usuario));
    }

    @Test
    public void permite_si_el_usuario_es_valido_de_tipo_kraal() throws Exception {
        Usuario usuario = usuarioDeGrupoConPermisos();
        when(usuario.getTipo()).thenReturn(TipoAsociado.Kraal);
        assertTrue(service.puedeEditarUsuariosDelGrupo(grupoId, usuario));
    }

    @Test
    public void permite_si_el_usuario_es_valido_de_tipo_comite() throws Exception {
        Usuario usuario = usuarioDeGrupoConPermisos();
        when(usuario.getTipo()).thenReturn(TipoAsociado.Comite);
        assertTrue(service.puedeEditarUsuariosDelGrupo(grupoId, usuario));
    }

    @Test
    public void meta_el_usuario_del_grupo_puede_editar() throws Exception {
        Usuario usuario = usuarioDeGrupoConPermisos();
        assertTrue(service.puedeEditarUsuariosDelGrupo(grupoId, usuario));
    }

    @Test
    public void meta_el_tecnico_fev_puede_editar() throws Exception {
        Usuario fev = tecnicoFev();
        assertTrue(service.puedeEditarUsuariosDelGrupo(grupoId, fev));
    }

    private Usuario usuarioDeGrupoConPermisos() {
        Usuario usuario = mock(Usuario.class);
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn(grupoId);
        when(usuario.getGrupo()).thenReturn(grupo);
        when(usuario.isUsuarioActivo()).thenReturn(true);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Kraal);
        when(usuario.getRestricciones()).thenReturn(new Restricciones());
        return usuario;
    }

    private Usuario tecnicoFev() {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getGrupo()).thenReturn(null);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.isUsuarioActivo()).thenReturn(true);
        when(usuario.getRestricciones()).thenReturn(new Restricciones());
        return usuario;
    }
}

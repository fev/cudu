package org.scoutsfev.cudu.services;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.GrupoRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTests {

    private AsociadoRepository asociadoRepository;
    private AuthorizationService service;
    private GrupoRepository grupoRepository;

    @Before
    public void setUp() throws Exception {
        asociadoRepository = mock(AsociadoRepository.class);
        grupoRepository = mock(GrupoRepository.class);
        service = new AuthorizationService(asociadoRepository, grupoRepository);
    }

    @Test
    public void no_puedeEditarAsociado_si_el_asociado_o_el_usuario_son_nulos() throws Exception {
        assertFalse(service.puedeEditarAsociado((Asociado)null, mock(Usuario.class)));
        assertFalse(service.puedeEditarAsociado(42, null));
        assertFalse(service.puedeEditarAsociado((Asociado)null, null));
    }

    @Test
    public void puedeEditarAsociado_cuando_el_asociado_pertenece_al_grupo_del_usuario_y_no_tiene_restricciones() throws Exception {
        when(asociadoRepository.obtenerCodigoDeGrupoDelAsociado(42)).thenReturn("ANY");
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("ANY");
        Usuario usuario = mock(Usuario.class);
        when(usuario.getGrupo()).thenReturn(grupo);
        assertTrue(service.puedeEditarAsociado(42, usuario));
    }

    @Test
    public void no_puedeEditarAsociado_cuando_el_asociado_no_pertenece_al_grupo_del_usuario() throws Exception {
        when(asociadoRepository.obtenerCodigoDeGrupoDelAsociado(42)).thenReturn("ANY");
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("OTHER");
        Usuario usuario = mock(Usuario.class);
        when(usuario.getGrupo()).thenReturn(grupo);
        assertFalse(service.puedeEditarAsociado(42, usuario));
    }

    @Test
    public void cuando_el_grupo_del_usuario_es_nulo_no_puede_editar_asociados() throws Exception {
        when(asociadoRepository.obtenerCodigoDeGrupoDelAsociado(42)).thenReturn("ANY");
        Usuario usuario = mock(Usuario.class);
        when(usuario.getGrupo()).thenReturn(null);
        assertFalse(service.puedeEditarAsociado(42, usuario));
    }
}

package org.scoutsfev.cudu.services;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoStorage;
import org.scoutsfev.cudu.storage.GrupoRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractAuthorizationServiceAsociadoTests {

    protected final boolean accesoParaEdicion;

    protected AuthorizationService service;
    protected AsociadoParaAutorizar asociado;
    protected Usuario usuario;

    public AbstractAuthorizationServiceAsociadoTests(boolean accesoParaEdicion) {
        this.accesoParaEdicion = accesoParaEdicion;
    }

    @Before
    public void setUp() throws Exception {
        AsociadoStorage asociadoStorage = mock(AsociadoStorage.class);
        GrupoRepository grupoRepository = mock(GrupoRepository.class);
        service = new AuthorizationService(asociadoStorage, grupoRepository);
        asociado = mock(AsociadoParaAutorizar.class);
        when(asociado.getId()).thenReturn(33);
        usuario = mock(Usuario.class);
        when(usuario.getId()).thenReturn(42);
        when(usuario.isUsuarioActivo()).thenReturn(true);
    }

    @Test
    public void no_permite_si_el_asociado_es_nulo() throws Exception {
        assertFalse(service.comprobarAccesoAsociado(null, usuario, true));
    }

    @Test
    public void no_permite_si_el_usuario_es_nulo() throws Exception {
        assertFalse(service.comprobarAccesoAsociado(asociado, null, true));
    }

    @Test
    public void no_permite_si_el_usuario_y_el_asociado_son_nulos() throws Exception {
        assertFalse(service.comprobarAccesoAsociado(null, null, true));
    }

    @Test
    public void no_permite_si_el_usuario_no_esta_activo() throws Exception {
        prepararCasoMismoGrupoSinRestricciones();
        when(usuario.isUsuarioActivo()).thenReturn(false);
        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_ver_o_editar_sus_propios_datos_cuando_viene_de_grupo_y_tiene_restricciones_solo_lectura() throws Exception {
        Restricciones restricciones = prepararCasoMismoGrupoSinRestricciones();
        restricciones.setSoloLectura(true);
        int id = 3389;
        when(asociado.getId()).thenReturn(id);
        when(usuario.getId()).thenReturn(id);
        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_ver_o_editar_sus_propios_datos_cuando_es_tecnico_asociativo() throws Exception {
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Asociacion);
        comprobarQuePuedeEditarSusDatos();
    }

    @Test
    public void permite_ver_o_editar_sus_propios_datos_cuando_es_tecnico_asociativo_y_tiene_restriccion_de_solo_lectura() throws Exception {
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Asociacion);
        Restricciones restricciones = new Restricciones();
        restricciones.setSoloLectura(true);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        comprobarQuePuedeEditarSusDatos();
    }

    @Test
    public void permite_ver_o_editar_sus_propios_datos_cuando_es_tecnico_federativo() throws Exception {
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        comprobarQuePuedeEditarSusDatos();
    }

    @Test
    public void permite_ver_o_editar_sus_propios_datos_cuando_es_tecnico_federativo_y_tiene_restriccion_de_solo_lectura() throws Exception {
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        Restricciones restricciones = new Restricciones();
        restricciones.setSoloLectura(true);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        comprobarQuePuedeEditarSusDatos();
    }

    @Test
    public void permite_ver_o_editar_sus_propios_datos_con_cualquier_otro_tipo_de_asociado() throws Exception {
        for (TipoAsociado tipo : TipoAsociado.values()) {
            if (tipo == TipoAsociado.Joven || tipo == TipoAsociado.Kraal || tipo == TipoAsociado.Comite || tipo == TipoAsociado.Tecnico)
                continue;
            prepararCasoMismoGrupoSinRestricciones();
            when(usuario.getTipo()).thenReturn(tipo);
            comprobarQuePuedeEditarSusDatos();
        }
    }

    @Test
    public void permite_ver_o_editar_sus_propios_datos_con_cualquier_ambito_de_edicion() throws Exception {
        for (AmbitoEdicion ambito : AmbitoEdicion.values()) {
            prepararCasoMismoGrupoSinRestricciones();
            when(usuario.getAmbitoEdicion()).thenReturn(ambito);
            comprobarQuePuedeEditarSusDatos();
        }
    }

    @Test
    public void no_permite_ver_o_editar_datos_de_su_grupo_con_ambitos_de_edicion_distintos_de_federacion_asociacion_o_grupo() throws Exception {
        for (AmbitoEdicion ambito : AmbitoEdicion.values()) {
            if (ambito == AmbitoEdicion.Federacion || ambito == AmbitoEdicion.Asociacion || ambito == AmbitoEdicion.Grupo)
                continue;
            prepararCasoMismoGrupoSinRestricciones();
            when(usuario.getAmbitoEdicion()).thenReturn(ambito);
            assertFalse("Ambito con acceso: " + ambito, service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
        }
    }

    private void comprobarQuePuedeEditarSusDatos() {
        if (usuario.getRestricciones() == null)
            when(usuario.getRestricciones()).thenReturn(new Restricciones());
        int id = 3389;
        when(asociado.getId()).thenReturn(id);
        when(usuario.getId()).thenReturn(id);
        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_si_el_usuario_es_del_mismo_grupo_y_no_tiene_restricciones() throws Exception {
        prepararCasoMismoGrupoSinRestricciones();
        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_si_el_usuario_es_del_mismo_grupo_y_la_misma_rama_cuando_tiene_restricciones_de_rama() throws Exception {
        when(asociado.getGrupoId()).thenReturn("SOME");
        when(asociado.isRamaColonia()).thenReturn(true);

        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("SOME");
        when(usuario.getGrupo()).thenReturn(grupo);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Grupo);
        Restricciones restricciones = new Restricciones();
        restricciones.setNoPuedeEditarOtrasRamas(true);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        when(usuario.isRamaColonia()).thenReturn(true);

        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_si_el_usuario_es_del_mismo_grupo_y_la_misma_rama_cuando_tiene_restricciones_de_rama_habiendo_varias_ramas_implicadas() throws Exception {
        when(asociado.getGrupoId()).thenReturn("SOME");
        when(asociado.isRamaColonia()).thenReturn(true);
        when(asociado.isRamaExpedicion()).thenReturn(true);

        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("SOME");
        when(usuario.getGrupo()).thenReturn(grupo);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Grupo);
        Restricciones restricciones = new Restricciones();
        restricciones.setNoPuedeEditarOtrasRamas(true);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        when(usuario.isRamaColonia()).thenReturn(true);
        when(usuario.isRamaExpedicion()).thenReturn(true);

        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void no_permite_si_el_usuario_es_del_mismo_grupo_y_otra_rama_cuando_tiene_restricciones_de_rama() throws Exception {
        when(asociado.getGrupoId()).thenReturn("SOME");
        when(asociado.isRamaColonia()).thenReturn(false);

        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("SOME");
        when(usuario.getGrupo()).thenReturn(grupo);
        Restricciones restricciones = new Restricciones();
        restricciones.setNoPuedeEditarOtrasRamas(true);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        when(usuario.isRamaColonia()).thenReturn(true);

        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void no_permite_si_el_usuario_es_del_mismo_grupo_y_otra_rama_cuando_tiene_restricciones_de_rama_habiendo_varias_ramas_implicadas() throws Exception {
        when(asociado.getGrupoId()).thenReturn("SOME");
        when(asociado.isRamaColonia()).thenReturn(false);
        when(asociado.isRamaExpedicion()).thenReturn(true);

        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("SOME");
        when(usuario.getGrupo()).thenReturn(grupo);
        Restricciones restricciones = new Restricciones();
        restricciones.setNoPuedeEditarOtrasRamas(true);
        when(usuario.getRestricciones()).thenReturn(restricciones);
        when(usuario.isRamaColonia()).thenReturn(false);
        when(usuario.isRamaExpedicion()).thenReturn(false);

        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void no_permite_si_el_asociado_no_es_tecnico_y_es_de_otro_grupo() throws Exception {
        when(asociado.getGrupoId()).thenReturn("SOME");
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("ANY");
        when(usuario.getTipo()).thenReturn(TipoAsociado.Kraal);
        when(usuario.getGrupo()).thenReturn(grupo);
        when(usuario.getRestricciones()).thenReturn(new Restricciones());
        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_si_el_usuario_es_tecnico_federativo() throws Exception {
        prepararCasoTecnicoFederativo();
        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_si_el_usuario_es_tecnico_federativo_cuando_edita_usuarios_sin_grupo() throws Exception {
        prepararCasoTecnicoFederativo();
        when(asociado.getGrupoId()).thenReturn(null);
        assertNull(asociado.getGrupoId());
        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_si_el_usuario_es_tecnico_asociativo_y_ademas_el_grupo_pertenece_a_dicha_asociacion() throws Exception {
        prepararCasoTecnicoAsociactivo(Asociacion.MEV, Asociacion.MEV);
        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void no_permite_si_el_usuario_es_tecnico_asociativo_pero_el_grupo_pertenece_otra_asociacion() throws Exception {
        prepararCasoTecnicoAsociactivo(Asociacion.MEV, Asociacion.SdA);
        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void permite_que_el_tecnico_federativo_edite_asociados_sin_grupo() throws Exception {
        prepararCasoTecnicoFederativo();
        when(asociado.getGrupoId()).thenReturn(null);
        assertNull(asociado.getGrupoId());
        assertTrue(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    @Test
    public void no_permite_que_el_tecnico_asociativo_edite_asociados_sin_grupo() throws Exception {
        prepararCasoTecnicoAsociactivo(Asociacion.MEV, null);
        when(asociado.getGrupoId()).thenReturn(null);
        assertNull(asociado.getGrupoId());
        assertFalse(service.comprobarAccesoAsociado(asociado, usuario, accesoParaEdicion));
    }

    protected Restricciones prepararCasoMismoGrupoSinRestricciones() {
        when(asociado.getGrupoId()).thenReturn("SOME");
        Grupo grupo = mock(Grupo.class);
        when(grupo.getId()).thenReturn("SOME");
        when(usuario.getGrupo()).thenReturn(grupo);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Grupo);
        Restricciones restricciones = new Restricciones();
        when(usuario.getRestricciones()).thenReturn(restricciones);
        return restricciones;
    }

    protected void prepararCasoTecnicoAsociactivo(Asociacion asociacionDelTecnico, Asociacion asociacionDelGrupo) {
        when(asociado.getGrupoId()).thenReturn("SOME");
        if (asociacionDelGrupo != null)
            when(asociado.getAsociacionId()).thenReturn(asociacionDelGrupo.getId());
        else
            when(asociado.getAsociacionId()).thenReturn(null);
        Grupo grupo = mock(Grupo.class);
        when(grupo.getAsociacion()).thenReturn(asociacionDelGrupo);
        when(grupo.getId()).thenReturn("SOME");

        when(usuario.getGrupo()).thenReturn(null);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Asociacion);
        Restricciones restricciones = new Restricciones();
        restricciones.setRestriccionAsociacion(asociacionDelTecnico);
        when(usuario.getRestricciones()).thenReturn(restricciones);
    }

    protected Restricciones prepararCasoTecnicoFederativo() {
        when(asociado.getGrupoId()).thenReturn("SOME");
        when(usuario.getGrupo()).thenReturn(null);
        when(usuario.getTipo()).thenReturn(TipoAsociado.Tecnico);
        when(usuario.getAmbitoEdicion()).thenReturn(AmbitoEdicion.Federacion);
        Restricciones restricciones = new Restricciones();
        when(usuario.getRestricciones()).thenReturn(restricciones);
        return restricciones;
    }
}

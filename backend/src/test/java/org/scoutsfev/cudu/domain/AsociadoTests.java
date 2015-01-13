package org.scoutsfev.cudu.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AsociadoTests {

    private Grupo grupo;
    private Asociado asociado;

    @Before
    public void setUp() throws Exception {
        grupo = new Grupo("TEST", Asociacion.MEV, "Placeholder", "ABCDEF", 46015, "Valencia", "963400000", "test@example.com");
        asociado = new Asociado(grupo, TipoAsociado.Joven, AmbitoEdicion.Grupo, "Mike", "Wazowski", new Date());
    }

    @Test
    public void por_defecto_un_asociado_esta_activado() throws Exception {
        assertTrue(asociado.isActivo());
    }

    @Test
    public void por_defecto_el_usuario_de_un_asociado_esta_activado() throws Exception {
        assertFalse(asociado.isUsuarioActivo());
    }

    @Test
    public void por_defecto_un_asociado_no_esta_en_ninguna_rama() throws Exception {
        assertFalse(asociado.isRamaCastores());
        assertFalse(asociado.isRamaLobatos());
        assertFalse(asociado.isRamaExploradores());
        assertFalse(asociado.isRamaPioneros());
        assertFalse(asociado.isRamaRuta());
    }

    @Test
    public void por_defecto_las_restricciones_de_un_usuario_no_son_nulas() throws Exception {
        Usuario usuario = new Usuario();
        assertNotNull(usuario.getRestricciones());
    }
}

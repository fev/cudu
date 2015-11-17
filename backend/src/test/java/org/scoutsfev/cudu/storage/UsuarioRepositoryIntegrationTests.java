package org.scoutsfev.cudu.storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.scoutsfev.cudu.Application;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;
import org.scoutsfev.cudu.support.TestIntegracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Category(TestIntegracion.class)
public class UsuarioRepositoryIntegrationTests {

    private static AtomicInteger secuencia = new AtomicInteger(1);

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Asociado asociado;

    @Before
    public void setUp() throws Exception {
        Asociado nuevoAsociado = GeneradorDatosDePrueba.generarAsociado();
        nuevoAsociado.setEmail("jack.sparrow." + secuencia.getAndIncrement() + "@example.com");
        asociado = asociadoRepository.save(nuevoAsociado);
    }

    @Test
    public void al_activar_un_usuario_se_establece_el_password_y_se_habilita_el_flag_de_usuarioActivo() throws Exception {
        usuarioRepository.activar(asociado.getId(), "12345", (short)0);
        Usuario usuario = usuarioRepository.findByEmail(asociado.getEmail());
        assertEquals("12345", usuario.getPassword());
        assertTrue(usuario.isUsuarioActivo());
    }

    @Test
    public void al_desactivar_un_usuario_se_establece_el_password_a_nulo_y_se_deshabilita_el_flag_de_usuarioActivo() throws Exception {
        usuarioRepository.desactivar(asociado.getId());
        Usuario usuario = usuarioRepository.findByEmail(asociado.getEmail());
        assertNull(usuario.getPassword());
        assertFalse(usuario.isUsuarioActivo());
    }

    @Test
    public void por_defecto_el_idioma_del_usuario_es_nulo() throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(asociado.getEmail());
        assertNull(usuario.getLenguaje());
    }

    @Test
    public void es_posible_cambiar_el_idioma_de_cualquier_usuario() throws Exception {
        usuarioRepository.cambiarIdioma(asociado.getId(), "CA");
        Usuario usuario = usuarioRepository.findByEmail(asociado.getEmail());
        assertEquals("CA", usuario.getLenguaje());
    }
}

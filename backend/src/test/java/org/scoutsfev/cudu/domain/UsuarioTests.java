package org.scoutsfev.cudu.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UsuarioTests {

    @Test
    public void toString_devuelve_una_cadena_que_contiene_el_login() throws Exception {
        String login = "jack.sparrow@example.com";
        Usuario usuario = new Usuario(login, null, null, true, null);
        assertEquals(login, usuario.getUsername());
        assertTrue(usuario.toString().contains(login));
    }
}

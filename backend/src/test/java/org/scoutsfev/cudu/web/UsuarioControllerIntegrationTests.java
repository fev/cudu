package org.scoutsfev.cudu.web;

import org.junit.Test;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.support.EndToEndTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class UsuarioControllerIntegrationTests extends EndToEndTest {

    private Usuario usuario;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        usuario = generarUsuarioEnGrupo();
    }

    @Test
    public void si_el_email_del_usuario_es_nulo_o_vacio_devuelve_400() throws Exception {
        devuelveErrorAlHacerLogin(null, usuario.getPassword(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void si_el_password_del_usuario_es_nulo_o_vacio_devuelve_400() throws Exception {
        devuelveErrorAlHacerLogin(usuario.getUsername(), null, HttpStatus.BAD_REQUEST);
        devuelveErrorAlHacerLogin(usuario.getUsername(), "", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void si_el_email_del_usuario_no_esta_bien_formado_devuelve_400() throws Exception {
        devuelveErrorAlHacerLogin(usuario.getEmail().replace('@', 'z'), usuario.getPassword(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void si_el_password_tiene_menos_de_6_caracteres_devuelve_400() throws Exception {
        devuelveErrorAlHacerLogin(usuario.getEmail(), "1234", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void si_las_credenciales_son_correctas_devuelve_200_y_el_usuario() throws Exception {
        ResponseEntity<Usuario> respuesta = login(usuario.getEmail(), usuario.getPassword(), Usuario.class, false);
        assertThat(respuesta.getStatusCode(), equalTo(HttpStatus.OK));
        Usuario u = respuesta.getBody();
        assertNotNull(u);
        assertEquals(usuario.getId(), u.getId());
    }

    @Test
    public void cuando_las_credenciales_son_incorrectas_devuelve_403() throws Exception {
        devuelveErrorAlHacerLogin(usuario.getEmail(), usuario.getPassword() + "1", HttpStatus.FORBIDDEN);
    }

    @Test
    public void al_obtener_el_usuario_actual_este_no_contiene_el_password() throws Exception {
        login(usuario.getEmail(), usuario.getPassword());
        ResponseEntity<Usuario> response = template.getForEntity(endpoint("/usuario/actual"), Usuario.class);
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertEquals(usuario.getEmail(), response.getBody().getEmail());
        assertNull(response.getBody().getPassword());
    }

    // TODO Integration test: si_el_usuario_no_esta_autenticado_devuelve_403_en_usuario_actual
    // TODO Integration test: si_el_usuario_esta_autenticado_devuelve_200_y_el_usuario_actual_en_auth_check

    private void devuelveErrorAlHacerLogin(String login, String password, HttpStatus status) {
        try {
            ResponseEntity<Usuario> respuesta = login(login, password, Usuario.class, false);
            fail("Debería devolver un error, actual: " + respuesta.getStatusCode());
        } catch (HttpClientErrorException error) {
            assertEquals(status, error.getStatusCode());
            assertThat(error.getResponseBodyAsString(), not(containsString("restricciones")));
        }
    }
}

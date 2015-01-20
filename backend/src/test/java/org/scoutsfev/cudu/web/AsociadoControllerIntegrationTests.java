package org.scoutsfev.cudu.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;
import org.scoutsfev.cudu.support.EndToEndTest;
import org.scoutsfev.cudu.web.validacion.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AsociadoControllerIntegrationTests extends EndToEndTest {

    private Usuario usuario;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        usuario = generarUsuarioEnGrupo();
        login(usuario.getEmail(), usuario.getPassword());
    }

    @Test
    public void puede_obtener_el_asociado_del_usuario_actual() throws Exception {
        Asociado asociado = template.getForObject(endpoint("/asociado/{id}"), Asociado.class, usuario.getId());
        assertEquals(usuario.getId(), asociado.getId());
        assertEquals(usuario.getEmail(), asociado.getEmail());
    }

    @Test
    public void al_obtener_un_asociado_este_no_contiene_el_password() throws Exception {
        String respuesta = template.getForObject(endpoint("/asociado/{id}"), String.class, usuario.getId());
        assertThat(respuesta, containsString(usuario.getUsername()));
        assertThat(respuesta, not(containsString(usuario.getPassword())));
    }

    @Test
    public void al_crear_un_asociado_con_algun_error_de_validacion_el_controlador_devuelve_el_error_en_un_json() throws IOException {
        Asociado asociado = GeneradorDatosDePrueba.generarAsociado();
        asociado.setNombre(null);

        try {
            template.postForObject(endpoint("/asociado"), asociado, Asociado.class);
            fail("Se esperaba error HttpClientErrorException.");
        } catch (HttpClientErrorException error) {
            assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
            assertEquals(applicationJsonUtf8, error.getResponseHeaders().getContentType());
            List<Error> errores = new ObjectMapper().readValue(error.getResponseBodyAsString(), new TypeReference<List<Error>>(){});
            assertThat(errores.size(), is(greaterThanOrEqualTo(1)));
            assertTrue(Iterables.any(errores, e -> e.getCampo().equals("nombre")));
        }
    }
}

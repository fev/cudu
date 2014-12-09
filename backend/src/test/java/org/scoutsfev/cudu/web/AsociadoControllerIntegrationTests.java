package org.scoutsfev.cudu.web;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.scoutsfev.cudu.Application;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.scoutsfev.cudu.support.TestIntegracion;
import org.scoutsfev.cudu.web.validacion.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

@Category(TestIntegracion.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AsociadoControllerIntegrationTests {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("http://localhost:${local.server.port}")
    private String baseUrl;

    private static AtomicInteger idPool = new AtomicInteger(0);
    private Usuario usuario;
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        final String login = "dude" + idPool.getAndIncrement() + "@example.com";
        final String password = "wackamole";
        Asociado nuevo = GeneradorDatosDePrueba.generarAsociado(null);
        nuevo.setTipo(TipoAsociado.Voluntario);
        nuevo.setEmail(login);
        Asociado asociado = asociadoRepository.save(nuevo);

        usuarioRepository.activar(asociado.getId(), password);
        usuario = usuarioRepository.findByEmail(login);
        assumeNotNull(usuario);
        assumeThat(usuario.getEmail(), is(equalTo(asociado.getEmail())));

        template = new TestRestTemplate(usuario.getUsername(), usuario.getPassword(),
                TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    }

    @Test
    public void al_obtener_un_asociado_este_no_contiene_el_password() throws Exception {
        String response = template.getForObject(endpoint("/asociado/{id}"), String.class, usuario.getId());
        assertThat(response, containsString(usuario.getUsername()));
        assertThat(response, not(containsString(usuario.getPassword())));
    }

    @Test
    public void al_obtener_el_usuario_actual_este_no_contiene_el_password() throws Exception {
        ResponseEntity<Usuario> response = template.getForEntity(endpoint("/usuario"), Usuario.class);
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertEquals(usuario.getEmail(), response.getBody().getEmail());
        assertNull(response.getBody().getPassword());
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void al_crear_un_asociado_con_algun_error_de_validacion_el_controlador_devuelve_el_error_en_un_json() throws Exception {
        Asociado asociado = GeneradorDatosDePrueba.generarAsociado(null);
        asociado.setNombre(null);
        ResponseEntity<List<Error>> response = template.exchange(endpoint("/asociado"), HttpMethod.POST,
                new HttpEntity<>(asociado), new ParameterizedTypeReference<List<Error>>() { });
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        List<Error> errores = response.getBody();
        assertThat(errores.size(), is(greaterThanOrEqualTo(1)));
        assertTrue(Iterables.any(errores, e -> e.getCampo().equals("nombre")));
    }

    private String endpoint(String relative) {
        return baseUrl + relative;
    }
}

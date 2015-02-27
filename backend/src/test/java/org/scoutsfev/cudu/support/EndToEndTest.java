package org.scoutsfev.cudu.support;

import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.scoutsfev.cudu.Application;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@Category(TestIntegracion.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public abstract class EndToEndTest {

    private static final AtomicInteger idPool = new AtomicInteger(0);

    protected static final MediaType applicationJsonUtf8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    protected AsociadoRepository asociadoRepository;

    @Autowired
    protected GrupoRepository grupoRepository;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Value("http://localhost:${local.server.port}")
    private String baseUrl;

    protected RestTemplate template;
    protected CookieInterceptor cookieInterceptor;

    @Before
    public void setUp() throws Exception {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        cookieInterceptor = new CookieInterceptor();
        template = new RestTemplate(new InterceptingClientHttpRequestFactory(requestFactory, Arrays.asList(cookieInterceptor)));
    }

    protected Usuario login(String email, String password) {
        ResponseEntity<Usuario> respuesta = login(email, password, Usuario.class, true);
        assertThat(respuesta.getStatusCode(), equalTo(HttpStatus.OK));
        return respuesta.getBody();
    }

    protected <T> ResponseEntity<T> login(String email, String password, Class<T> responseType, boolean establecerCookies) {
        Credenciales credenciales = new Credenciales(email, password);
        ResponseEntity<T> respuesta = template.postForEntity(endpoint("/usuario/autenticar"), credenciales, responseType);
        if (establecerCookies)
            establecerCookies(respuesta, this.cookieInterceptor);
        return respuesta;
    }

    protected void establecerCookies(ResponseEntity respuesta, CookieInterceptor interceptor) {
        String rawHeader = respuesta.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        List<HttpCookie> cookies = HttpCookie.parse(rawHeader);
        interceptor.setCookies(cookies);
    }

    protected Usuario generarUsuarioEnGrupo() {
        final String login = "baden." + idPool.getAndIncrement() + "@example.com";
        final String password = "wackamole";

        Grupo grupo = GeneradorDatosDePrueba.generarGrupo(Optional.of("G" + idPool.getAndIncrement()));
        grupoRepository.save(grupo);

        Asociado nuevo = GeneradorDatosDePrueba.generarAsociado(grupo);
        nuevo.setTipo(TipoAsociado.Kraal);
        nuevo.setRamaExpedicion(true);
        nuevo.setFechaNacimiento(LocalDate.now().minus(20, ChronoUnit.YEARS));
        nuevo.setDni("12345678Z");
        nuevo.setEmail(login);
        Asociado asociado = asociadoRepository.save(nuevo);

        usuarioRepository.activar(asociado.getId(), password);
        Usuario usuario = usuarioRepository.findByEmail(login);
        assertNotNull(usuario);
        assertThat(usuario.getEmail(), is(equalTo(asociado.getEmail())));
        return usuario;
    }

    protected String endpoint(String relative) {
        return baseUrl + relative;
    }
}

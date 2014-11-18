package org.scoutsfev.cudu.web;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.scoutsfev.cudu.Application;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.AsociadoRepositoryIntegrationTests;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.scoutsfev.cudu.support.TestIntegracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Category(TestIntegracion.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AsociadoControllerIntegrationTests {

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AsociadoController asociadoController;

    @Value("${local.server.port}")
    private int puerto;

    @Test
    public void al_obtener_un_asociado_este_no_contiene_el_password() throws Exception {
        final String login = "dude@example.com";
        final String password = "wackamole";
        Asociado nuevo = AsociadoRepositoryIntegrationTests.generarAsociado(null);
        nuevo.setEmail(login);
        Asociado asociado = asociadoRepository.save(nuevo);
        usuarioRepository.activar(asociado.getId(), password);

        RestTemplate template = new TestRestTemplate(login, password, TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
        String response = template.getForObject("http://localhost:" + puerto + "/asociado/{id}", String.class, asociado.getId());
        assertThat(response, containsString(asociado.getNombre()));
        assertThat(response, not(containsString(password)));
    }
}

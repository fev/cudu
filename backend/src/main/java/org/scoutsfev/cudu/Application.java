package org.scoutsfev.cudu;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.TipoAsociado;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        AsociadoRepository asociadoRepository = context.getBean(AsociadoRepository.class);
        Asociado asociado = new Asociado(null, TipoAsociado.Voluntario, "Baden", "Powell", new Date());
        asociado.setEmail("baden@scoutsfev.org");
        asociadoRepository.save(asociado);

        UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);
        usuarioRepository.activar(asociado.getId(), "1234");
    }
}
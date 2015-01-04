package org.scoutsfev.cudu;

import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.GrupoRepository;
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
        GrupoRepository grupoRepository = context.getBean(GrupoRepository.class);
        UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);

        Asociado fev = new Asociado(null, TipoAsociado.Tecnico, AmbitoEdicion.Federacion, "Tecnico", "FEV", new Date());
        fev.setEmail("fev@scoutsfev.org");
        asociadoRepository.save(fev);
        usuarioRepository.activar(fev.getId(), "test");
        usuarioRepository.cambiarIdioma(fev.getId(), "ca");

        Asociado sda = new Asociado(null, TipoAsociado.Tecnico, AmbitoEdicion.Asociacion, "Tecnico", "SdA", new Date());
        sda.setEmail("sda@scoutsfev.org");
        asociadoRepository.save(sda);
        usuarioRepository.activar(sda.getId(), "test");
        usuarioRepository.establecerRestricciones(sda.getId(), false, false, false, Asociacion.SdA);

        Asociado lluerna = new Asociado(null, TipoAsociado.Tecnico, AmbitoEdicion.Escuela, "Tecnico", "Lluerna", new Date());
        lluerna.setEmail("lluerna@scoutsfev.org");
        asociadoRepository.save(lluerna);
        usuarioRepository.activar(lluerna.getId(), "test");

        Grupo grupo = new Grupo("UP", Asociacion.MEV, "X de la Educación", "Calle", 46015, "Valencia", "Valencia", "963400000", "email@example.com");
        grupoRepository.save(grupo);
        Asociado baden = new Asociado(grupo, TipoAsociado.Kraal, AmbitoEdicion.Grupo, "Baden", "Powell", new Date(457819200));
        baden.setEmail("baden@example.com");
        asociadoRepository.save(baden);
        usuarioRepository.activar(baden.getId(), "test");

        // curl -i -w '\n' -u sda@scoutsfev.org:test localhost:9000/api/usuario
    }
}
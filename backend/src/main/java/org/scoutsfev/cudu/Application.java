package org.scoutsfev.cudu;

import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        Grupo[] grupos = new Grupo[] {
                new Grupo("AK", Asociacion.MEV, "Ain-Karen", "ABCDEF", 46015, "Valencia", "Valencia", "963400000", "ainkaren@gmail.com"),
                new Grupo("AR", Asociacion.MEV, "Argila", "OPQRST", 46970, "Valencia", "Valencia", "963400000", "senarojo2@hotmail.com"),
                new Grupo("XPI", Asociacion.MEV, "X El Pilar", "OPQRST", 46189, "Valencia", "Valencia", "963400000", "xpilar@gmail.com")
        };
        GrupoRepository grupoRepository = ctx.getBean(GrupoRepository.class);
        grupoRepository.save(Arrays.asList(grupos));

        AsociadoRepository asociadoRepository = ctx.getBean(AsociadoRepository.class);
        Asociado asociado01 = new Asociado(grupos[1], TipoAsociado.Joven, Rama.Castores, "Mike", "Wazowski", new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);
        asociado01.setDni("12345678Z");
		   
        Asociado asociado02 = new Asociado(grupos[1], TipoAsociado.Kraal, Rama.Pioneros, "Jack", "Sparrow", new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);
		Asociado asociado03 = new Asociado(grupos[1], TipoAsociado.Comite, Rama.Exploradores, "Ned", "Stark", new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);
		Asociado asociado04 = new Asociado(grupos[1], TipoAsociado.Kraal, Rama.Exploradores, "Frodo", "Bolson", new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);
		Asociado asociado05 = new Asociado(grupos[1], TipoAsociado.Joven, Rama.Lobatos, "Tyrion", "Lanister", new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);
		Asociado asociado06 = new Asociado(grupos[1], TipoAsociado.Kraal, Rama.Ruta, "John", "Snow", new Date(190), "Calle", 46015, "Valencia", Sexo.Masculino);

        asociadoRepository.save(Arrays.asList(asociado01, asociado02, asociado03, asociado04, asociado05, asociado06));

        int asociadoId3 = asociado03.getId();
        asociadoRepository.activar(asociadoId3, false);
        asociadoRepository.activar(asociadoId3, true);

        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        // String password = passwordEncoder.encode("whatever");
        UsuarioRepository usuarioRepository = ctx.getBean(UsuarioRepository.class);
        Usuario usuario = new Usuario("mike", "whatever", "Mike Wazowski", true, new String[] { "ADMIN" });
        usuario.setGrupo(grupos[1]);
        usuarioRepository.save(usuario);
		Usuario usuario2 = new Usuario("baden", "powell", "Baden Powell", true, new String[] { "ADMIN" });
        usuario2.setGrupo(grupos[0]);
        usuarioRepository.save(usuario2);
    }
}
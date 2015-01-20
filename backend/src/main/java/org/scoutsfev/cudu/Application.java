package org.scoutsfev.cudu;

import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;
import java.util.Date;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        AsociadoRepository asociadoRepository = context.getBean(AsociadoRepository.class);
        GrupoRepository grupoRepository = context.getBean(GrupoRepository.class);
        UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);

        /*
        Asociado fev = new Asociado(null, TipoAsociado.Tecnico, AmbitoEdicion.Federacion, "Tecnico", "FEV", new Date());
        fev.setEmail("fev@scoutsfev.org");
        asociadoRepository.save(fev);
        usuarioRepository.activar(fev.getId(), "wackamole");
        usuarioRepository.cambiarIdioma(fev.getId(), "ca");

        Asociado sda = new Asociado(null, TipoAsociado.Tecnico, AmbitoEdicion.Asociacion, "Tecnico", "SdA", new Date());
        sda.setEmail("sda@scoutsfev.org");
        asociadoRepository.save(sda);
        usuarioRepository.activar(sda.getId(), "wackamole");
        usuarioRepository.establecerRestricciones(sda.getId(), false, false, false, Asociacion.SdA);

        Asociado lluerna = new Asociado(null, TipoAsociado.Tecnico, AmbitoEdicion.Escuela, "Tecnico", "Lluerna", new Date());
        lluerna.setEmail("lluerna@scoutsfev.org");
        asociadoRepository.save(lluerna);
        usuarioRepository.activar(lluerna.getId(), "wackamole");

        Grupo grupo = new Grupo("UP", Asociacion.MEV, "X de la Educación", "Calle", 46015, "Valencia", "963400000", "email@example.com");
        grupoRepository.save(grupo);
        Asociado baden = new Asociado(grupo, TipoAsociado.Kraal, AmbitoEdicion.Grupo, "Baden", "Powell", new Date(457819200));
        baden.setEmail("baden@example.com");
        asociadoRepository.save(baden);
        usuarioRepository.activar(baden.getId(), "wackamole");
        */

        final Grupo ainKaren = grupoRepository.findOne("AK");
        final Usuario baden = usuarioRepository.findByEmail("baden@example.com");
        if (baden == null) {
            Asociado nuevo = new Asociado(ainKaren.getId(), TipoAsociado.Kraal, AmbitoEdicion.Grupo, "Baden", "Powell", LocalDate.of(1982, 1, 31));
            nuevo.setEmail("baden@example.com");
            asociadoRepository.save(nuevo);
            usuarioRepository.activar(nuevo.getId(), "wackamole");
        }

        asociadoRepository.findByGrupoId("AK", new PageRequest(0, 200));

        // curl -i -w '\n' -u sda@scoutsfev.org:test localhost:9000/api/usuario
    }

    @Autowired
    private MessageSource messageSource;

    @Bean
    public LocalValidatorFactoryBean validator() {
        // Configuramos NHibernate para que recoja las traducciones de messages.properties
        // en lugar de la localización por defecto (validation_messages.properties).
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
}
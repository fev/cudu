package org.scoutsfev.cudu;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.scoutsfev.cudu.domain.CacheKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Arrays;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
public class Application extends WebMvcConfigurerAdapter {

    @Autowired
    private MessageSource messageSource;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache(CacheKeys.DatosGraficasGlobales),
                new ConcurrentMapCache(CacheKeys.DatosGraficasPorGrupoRama),
                new ConcurrentMapCache(CacheKeys.DatosGraficasPorGrupoTipo)));
        return cacheManager;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public Jackson2ObjectMapperBuilder configurarSerializacionJson() {
        return new Jackson2ObjectMapperBuilder().modulesToInstall(Hibernate4Module.class);
    }

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

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
        // NO establecer un idioma por defecto o de lo contrario no resuelve mediante
        // Accept-Language http://git.io/A2hT (contradice docs de SessionLocaleResolver).
        //localeResolver.setDefaultLocale(StringUtils.parseLocaleString("es"));
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }
}
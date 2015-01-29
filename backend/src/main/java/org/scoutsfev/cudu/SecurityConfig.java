package org.scoutsfev.cudu;

import org.scoutsfev.cudu.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().requireCsrfProtectionMatcher()
        //http.sessionManagement().maximumSessions(1);
        http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/404").permitAll()
            .antMatchers("/reset/*").permitAll()
            .antMatchers("/resetnew/**").permitAll()
            .antMatchers("/usuario/autenticar").permitAll()
            .antMatchers("/graficas/*").permitAll()
            .anyRequest().authenticated();

        // TODO AuditEvent en log no muestra la ruta de la peticion
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService);
         // .passwordEncoder(new BCryptPasswordEncoder(16));
    }
}

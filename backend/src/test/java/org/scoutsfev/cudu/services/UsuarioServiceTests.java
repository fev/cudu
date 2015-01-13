package org.scoutsfev.cudu.services;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.TokenRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class UsuarioServiceTests {

    private UsuarioRepository repository;
    private UsuarioService service;
    private TokenRepository tokenRepository;

    @Before
    public void setUp() throws Exception {
        repository = mock(UsuarioRepository.class);
        tokenRepository = mock(TokenRepository.class);
        service = new UsuarioService(repository, tokenRepository, null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void usuarios_nulos_producen_UsernameNotFoundException() {
        service.loadUserByUsername(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void usuarios_vacios_producen_UsernameNotFoundException() {
        service.loadUserByUsername("");
    }

    @Test
    public void si_el_usuario_existe_lo_devuelve() {
        String username = "jack.sparrow";
        Usuario jackSparrow = mock(Usuario.class);
        when(jackSparrow.isUsuarioActivo()).thenReturn(true);
        when(repository.findByEmail(username)).thenReturn(jackSparrow);
        UserDetails userDetails = service.loadUserByUsername(username);
        assertEquals(jackSparrow, userDetails);
        verify(repository, times(1)).findByEmail(username);
        verify(repository, times(1)).findByEmail(anyString());
    }

    @Test
    public void si_el_usuario_no_existe_en_bbdd_lanza_UsernameNotFoundException() {
        when(repository.findByEmail(anyString())).thenReturn(null);
        buscarUsuarioEsperandoExcepcion("mike", UsernameNotFoundException.class);
        verify(repository, times(1)).findByEmail(anyString());
    }

    @Test
    public void si_el_usuario_esta_desactivado_lanza_DisabledException() throws Exception {
        String username = "jack.sparrow";
        Usuario jackSparrow = mock(Usuario.class);
        when(jackSparrow.isUsuarioActivo()).thenReturn(false);
        when(repository.findByEmail(username)).thenReturn(jackSparrow);
        buscarUsuarioEsperandoExcepcion("jack.sparrow", DisabledException.class);
    }

    @Test
    public void si_el_usuario_es_nulo_no_se_produce_llamada_a_bbdd() {
        buscarUsuarioEsperandoExcepcion(null, UsernameNotFoundException.class);
        verify(repository, never()).findByEmail(null);
    }

    @Test
    public void si_el_usuario_es_vacio_no_se_produce_llamada_a_bbdd() {
        buscarUsuarioEsperandoExcepcion("", UsernameNotFoundException.class);
        verify(repository, never()).findByEmail("");
    }

    private void buscarUsuarioEsperandoExcepcion(String username, Class exceptionClass) {
        try {
            service.loadUserByUsername(username);
        } catch (Exception exception) {
            Assert.isInstanceOf(exceptionClass, exception);
        }
    }

    // TODO si_el_usuario_esta_activo_y_tiene_password_puede_hacer_login
    // TODO si_el_usuario_esta_activo_pero_no_tiene_password_o_esta_en_blanco_no_puede_hacer_login
    // TODO si_el_usuario_esta_inactivo_no_puede_hacer_login
    // TODO si_el_usuario_es_menor_de_18_no_puede_hacer_login
    // TODO al_desactivar_el_usuario_su_password_es_nulo

    // TODO al_comprobar_captcha_si_no_se_encuentra_el_usuario_no_lanza_excepcion_ni_publica_evento
    // TODO al_comprobar_captcha_si_el_captcha_es_nulo_o_vacio_lanza_InvalidCaptchaException
    // TODO al_comprobar_captcha_si_el_captcha_es_nulo_audita_el_evento
    // TODO al_comprobar_captcha_si_la_verificacion_es_negativa_lanza_InvalidCaptchaException
    // TODO al_comprobar_captcha_si_la_verificacion_es_negativa_audita_el_evento
    // TODO al_comprobar_captcha_si_la_verificacion_es_positiva_devuelve_ok
    // TODO al_comprobar_captcha_si_la_verificacion_es_positiva_audita_el_evento
}

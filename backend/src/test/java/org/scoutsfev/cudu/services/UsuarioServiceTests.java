package org.scoutsfev.cudu.services;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Token;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.TokenRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class UsuarioServiceTests {

    private UsuarioRepository repository;
    private UsuarioService service;
    private TokenRepository tokenRepository;
    private AsociadoRepository asociadoRepository;

    @Before
    public void setUp() throws Exception {
        repository = mock(UsuarioRepository.class);
        tokenRepository = mock(TokenRepository.class);
        asociadoRepository = mock(AsociadoRepository.class);
        ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
        service = new UsuarioService(repository, tokenRepository, asociadoRepository, new NullEmailServiceImpl(), new NullCaptchaServiceImpl(), eventPublisher);
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

    @Test
    public void existeActivacionEnCurso_si_no_existe_token_en_bbdd_devuelve_false() throws Exception {
        String email = "jack.sparrow@example.com";
        when(tokenRepository.findByEmail(email)).thenReturn(null);
        assertFalse(service.existeActivacionEnCurso(email));
    }

    @Test
    public void existeActivacionEnCurso_si_existe_token_en_bbdd_pero_esta_caducado_lo_elimina_y_devulve_false() throws Exception {
        String email = "jack.sparrow@example.com";
        Token token = new Token(email, "ABCD", Instant.now().minus(1, ChronoUnit.HOURS), Duration.ofMinutes(1));
        assertTrue(token.expirado(Instant.now()));
        when(tokenRepository.findByEmail(email)).thenReturn(token);
        assertFalse(service.existeActivacionEnCurso(email));
        verify(tokenRepository, times(1)).delete(token);
    }

    @Test
    public void existeActivacionEnCurso_si_existe_token_en_bbdd_sin_caducar_devuelve_true() throws Exception {
        String email = "jack.sparrow@example.com";
        Token token = new Token(email, "ABCD", Instant.now(), Duration.ofMinutes(1));
        assertFalse(token.expirado(Instant.now()));
        when(tokenRepository.findByEmail(email)).thenReturn(token);
        assertTrue(service.existeActivacionEnCurso(email));
        verify(tokenRepository, never()).delete(token);
    }

    @Test(expected = AccountExpiredException.class)
    public void al_resetear_el_password_estando_el_usuario_desactivado_lanza_AccountExpiredException() throws Exception {
        String username = "jack.sparrow";
        Usuario jackSparrow = mock(Usuario.class);
        when(jackSparrow.isActivo()).thenReturn(true);
        when(jackSparrow.isUsuarioActivo()).thenReturn(false);
        when(repository.findByEmail(username)).thenReturn(jackSparrow);
        service.resetPassword(username, true);
    }

    @Test(expected = AccountExpiredException.class)
    public void al_resetear_el_password_estando_el_asociado_desactivado_lanza_AccountExpiredException() throws Exception {
        String username = "jack.sparrow";
        Usuario jackSparrow = mock(Usuario.class);
        when(jackSparrow.isActivo()).thenReturn(false);
        when(jackSparrow.isUsuarioActivo()).thenReturn(true);
        when(repository.findByEmail(username)).thenReturn(jackSparrow);
        service.resetPassword(username, true);
    }

    @Test
    public void si_el_reset_de_password_evita_el_check_de_activo_entonces_no_se_lanza_excepcion_aunque_el_usuario_este_inactivo() throws Exception {
        String username = "jack.sparrow";
        Usuario jackSparrow = mock(Usuario.class);
        when(jackSparrow.getEmail()).thenReturn(username);
        when(jackSparrow.isActivo()).thenReturn(false);
        when(jackSparrow.isUsuarioActivo()).thenReturn(false);
        when(repository.findByEmail(username)).thenReturn(jackSparrow);
        service.resetPassword(username, false);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void al_resetear_password_si_no_encuentra_el_usuario_por_email_lanza_UsernameNotFoundException() throws Exception {
        String username = "jack.sparrow";
        when(repository.findByEmail(username)).thenReturn(null);
        service.resetPassword(username, false);
    }

    @Test
    public void al_desactivar_un_usuario_sin_desactivar_el_asociado_el_asociado_no_se_desactiva() throws Exception {
        int asociadoId = 42;
        service.desactivarUsuario(asociadoId, false);
        verify(repository, times(1)).desactivar(asociadoId);
        verify(repository, times(1)).desactivar(anyInt());
        verify(repository, never()).activar(anyInt(), anyString(), anyShort());
        verify(asociadoRepository, never()).activar(anyInt(), anyBoolean());
    }

    @Test
    public void al_desactivar_un_usuario_desactivando_el_asociado_se_desactivan_ambos() throws Exception {
        int asociadoId = 33;
        service.desactivarUsuario(asociadoId, true);
        verify(repository, times(1)).desactivar(asociadoId);
        verify(repository, times(1)).desactivar(anyInt());
        verify(repository, never()).activar(anyInt(), anyString(), anyShort());
        verify(asociadoRepository, times(1)).activar(eq(asociadoId), eq(false));
        verify(asociadoRepository, never()).activar(not(eq(asociadoId)), eq(true));
    }

    // TODO si_el_usuario_esta_activo_y_tiene_password_puede_hacer_login
    // TODO si_el_usuario_esta_activo_pero_no_tiene_password_o_esta_en_blanco_no_puede_hacer_login
    // TODO si_el_usuario_esta_inactivo_no_puede_hacer_login
    // TODO si_el_usuario_es_menor_de_18_no_puede_hacer_login
    // TODO al_desactivar_el_usuario_su_password_es_nulo

    // TODO Kraal o comité no pueden ser menores de 18 años
    // TODO si se da de alta alguien como Kraal y se pasa a joven se reducen sus permisos a sus propios datos
    // TODO al pasar de kraal o comite a joven se desactiva su usuario automáticamente

    // TODO al_comprobar_captcha_si_no_se_encuentra_el_usuario_no_lanza_excepcion_ni_publica_evento
    // TODO al_comprobar_captcha_si_el_captcha_es_nulo_o_vacio_lanza_InvalidCaptchaException
    // TODO al_comprobar_captcha_si_el_captcha_es_nulo_audita_el_evento
    // TODO al_comprobar_captcha_si_la_verificacion_es_negativa_lanza_InvalidCaptchaException
    // TODO al_comprobar_captcha_si_la_verificacion_es_negativa_audita_el_evento
    // TODO al_comprobar_captcha_si_la_verificacion_es_positiva_devuelve_ok
    // TODO al_comprobar_captcha_si_la_verificacion_es_positiva_audita_el_evento
}

package org.scoutsfev.cudu.web;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;
import org.scoutsfev.cudu.services.UsuarioService;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.*;

public class UsuarioControllerTests {

    private final String nuevoEmail = "jack@sparrow.com";

    private AsociadoRepository asociadoRepository;
    private UsuarioController usuarioController;
    private Asociado asociado;
    private UsuarioService usuarioService;
    private AuthenticationManager authenticationManager;
    private Usuario usuario;

    @Before
    public void setUp() throws Exception {
        asociado = GeneradorDatosDePrueba.generarAsociado();
        usuario = mock(Usuario.class);
        asociado.setEmail(null);
        asociadoRepository = mock(AsociadoRepository.class);
        usuarioService = mock(UsuarioService.class);
        authenticationManager = mock(AuthenticationManager.class);
        usuarioController = new UsuarioController(asociadoRepository, usuarioService, authenticationManager);
    }

    @Test
    public void al_activar_un_usuario_se_debe_sobreescribir_el_email() throws Exception {
        assumeThat(asociado.getEmail(), is(nullValue()));
        usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        verify(asociadoRepository, times(1)).save(asociado);
        verifyNoMoreInteractions(asociadoRepository);
        assertEquals(nuevoEmail, asociado.getEmail());
    }

    @Test
    public void al_activar_un_usuario_se_lanza_el_procedimiento_para_resetear_el_password() throws Exception {
        ResponseEntity response = usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioService, times(1)).resetPassword(nuevoEmail);
        verify(usuarioService, times(1)).existeActivacionEnCurso(nuevoEmail);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    public void al_activar_un_usuario_activado_no_se_modifica_el_flag_de_usuario_activo() throws Exception {
        asociado.setUsuarioActivo(true);
        usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertTrue(asociado.isUsuarioActivo());
    }

    @Test
    public void al_activar_un_usuario_desactivado_no_se_modifica_el_flag_de_usuario_activo() throws Exception {
        asociado.setUsuarioActivo(false);
        usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertFalse(asociado.isUsuarioActivo());
    }

    @Test
    public void no_se_puede_activar_un_usuario_cuyo_asociado_esta_desactivado() throws Exception {
        asociado.setActivo(false);
        ResponseEntity response = usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(0)).resetPassword(nuevoEmail);
        verifyNoMoreInteractions(usuarioService);
    }

    // TODO al_hacer_login_por_primera_vez_con_credenciales_erroneas_devuelve_403_y_BadCredentialsException
    // TODO al_hacer_login_por_primera_vez_con_credenciales_erroneas_se_marca_requiereCaptcha
    // TODO al_hacer_login_por_segunda_vez_sin_captcha_devuelve_403_y_InvalidCaptchaException
    // TODO al_hacer_login_por_segunda_vez_con_captcha_invalido_devuelve_403_y_InvalidCaptchaException
    // TODO al_hacer_login_por_segunda_vez_con_captcha_valido_y_credenciales_ok_devuelve_200_y_el_usuario
}

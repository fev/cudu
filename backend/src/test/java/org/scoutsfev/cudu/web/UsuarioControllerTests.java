package org.scoutsfev.cudu.web;

import org.junit.Before;
import org.junit.Test;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Restricciones;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.generadores.GeneradorDatosDePrueba;
import org.scoutsfev.cudu.services.AuthorizationService;
import org.scoutsfev.cudu.services.UsuarioService;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.AsociadoStorage;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.scoutsfev.cudu.storage.UsuarioStorage;
import org.scoutsfev.cudu.web.validacion.CodigoError;
import org.scoutsfev.cudu.web.validacion.ErrorUnico;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
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
    private AuthorizationService authorizationService;
    private ApplicationEventPublisher eventPublisher;
    private UsuarioStorage usuarioStorage;
    private GrupoRepository grupoRepository;

    @Before
    public void setUp() throws Exception {
        Grupo grupo = GeneradorDatosDePrueba.generarGrupo(Optional.of("AK"));
        asociado = GeneradorDatosDePrueba.generarAsociado(grupo);
        asociado.setId(42);
        usuario = mock(Usuario.class);
        when(usuario.getGrupo()).thenReturn(grupo);
        when(usuario.getRestricciones()).thenReturn(new Restricciones());
        when(usuario.isUsuarioActivo()).thenReturn(true);
        asociado.setEmail(null);
        asociadoRepository = mock(AsociadoRepository.class);
        usuarioService = mock(UsuarioService.class);
        authenticationManager = mock(AuthenticationManager.class);
        eventPublisher = mock(ApplicationEventPublisher.class);
        grupoRepository = mock(GrupoRepository.class);
        AsociadoStorage asociadoStorage = mock(AsociadoStorage.class);
        authorizationService = new AuthorizationService(asociadoStorage, grupoRepository);
        usuarioStorage = mock(UsuarioStorage.class);
        usuarioController = new UsuarioController(asociadoRepository, usuarioService, eventPublisher, authenticationManager, authorizationService, usuarioStorage);
    }

    @Test
    public void al_activar_un_usuario_se_debe_sobreescribir_el_email_que_tuviera_previamente() throws Exception {
        assumeThat(asociado.getEmail(), is(nullValue()));
        usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        verify(asociadoRepository, times(1)).save(asociado);
        assertEquals(nuevoEmail, asociado.getEmail());
    }

    @Test
    public void al_activar_un_usuario_se_lanza_el_procedimiento_para_resetear_el_password() throws Exception {
        ResponseEntity response = usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioService, times(1)).resetPassword(anyString(), anyBoolean());
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
        verify(usuarioService, times(0)).resetPassword(anyString(), anyBoolean());
        verifyNoMoreInteractions(usuarioService);
        verify(asociadoRepository, times(0)).save(any(Asociado.class));
    }

    @Test
    public void al_activar_un_usuario_con_una_activacion_en_curso_devuelve_409_conflict_y_el_error_ActivacionDeUsuarioEnCurso() throws Exception {
        when(usuarioService.existeActivacionEnCurso(anyString())).thenReturn(true);
        ResponseEntity response = usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertThat("En el body no se devuelve un objeto de tipo ErrorUnico", response.getBody(), instanceOf(ErrorUnico.class));
        assertEquals(CodigoError.ActivacionDeUsuarioEnCurso.asError().getCodigo(), ((ErrorUnico)response.getBody()).getCodigo());
        verify(usuarioService, times(0)).resetPassword(anyString(), anyBoolean());
        verify(asociadoRepository, times(0)).save(any(Asociado.class));
    }

    @Test
    public void al_activar_un_usuario_con_un_email_que_ya_existe_devuelve_409_conflict_y_el_error_YaExisteUsuarioConEseEmail() throws Exception {
        when(asociadoRepository.existeOtroUsuarioConEseEmail(anyInt(), anyString())).thenReturn(true);
        ResponseEntity response = usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertThat("En el body no se devuelve un objeto de tipo ErrorUnico", response.getBody(), instanceOf(ErrorUnico.class));
        assertEquals(CodigoError.YaExisteUsuarioConEseEmail.asError().getCodigo(), ((ErrorUnico)response.getBody()).getCodigo());
        verify(usuarioService, times(0)).resetPassword(anyString(), anyBoolean());
        verify(asociadoRepository, times(0)).save(any(Asociado.class));
    }

    @Test
    public void no_se_permite_activar_el_usuario_actual_por_si_se_deja_la_sesion_abierta_el_controlador_devuelve_400_y_el_error_HabilitarUsuarioActual() throws Exception {
        asociado.setId(9);
        when(usuario.getId()).thenReturn(9);
        ResponseEntity response = usuarioController.activarUsuario(asociado, nuevoEmail, usuario);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertThat("En el body no se devuelve un objeto de tipo ErrorUnico", response.getBody(), instanceOf(ErrorUnico.class));
        assertEquals(CodigoError.HabilitarUsuarioActual.asError().getCodigo(), ((ErrorUnico)response.getBody()).getCodigo());
        verifyNoMoreInteractions(usuarioService);
        verifyNoMoreInteractions(asociadoRepository);
    }

    // TODO al_hacer_login_por_primera_vez_con_credenciales_erroneas_devuelve_403_y_BadCredentialsException
    // TODO al_hacer_login_por_primera_vez_con_credenciales_erroneas_se_marca_requiereCaptcha
    // TODO al_hacer_login_por_segunda_vez_sin_captcha_devuelve_403_y_InvalidCaptchaException
    // TODO al_hacer_login_por_segunda_vez_con_captcha_invalido_devuelve_403_y_InvalidCaptchaException
    // TODO al_hacer_login_por_segunda_vez_con_captcha_valido_y_credenciales_ok_devuelve_200_y_el_usuario

    // TODO al_entrar_como_lluerna_no_puede_listar_los_usuarios_de_un_grupo
    // TODO al_entrar_como_fev_puede_listar_los_usuarios_de_cualquier_grupo
    // TODO al_entrar_como_usuario_con_permisos_solo_puede_listar_los_usuarios_de_su_grupo_y_no_devuelve_error
    // TODO al_entrar_como_usuario_sin_permiso_para_editar_usuarios_no_puede_listar_los_usuarios_de_su_grupo
    // TODO al_entrar_como_usuario_sin_permiso_para_editar_su_grupo_no_puede_listar_los_usuarios_de_su_grupo
}

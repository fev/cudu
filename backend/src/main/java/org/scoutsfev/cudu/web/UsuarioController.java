package org.scoutsfev.cudu.web;

import org.hibernate.validator.constraints.Email;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Credenciales;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.UsuarioService;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.web.validacion.CodigoError;
import org.scoutsfev.cudu.web.validacion.ErrorUnico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final AsociadoRepository asociadoRepository;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UsuarioController(AsociadoRepository asociadoRepository, UsuarioService usuarioService, @Qualifier("authenticationManager") AuthenticationManager authenticationManager) {
        this.asociadoRepository = asociadoRepository;
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/autenticar", method = RequestMethod.POST)
    public ResponseEntity<Usuario> login(@RequestBody @Valid Credenciales credenciales, HttpServletRequest request) {
        // TODO Limpiar campos, sql, xss etc
        // TODO @RequestBody null?

        try {
            usuarioService.comprobarCaptcha(credenciales, request);
            Usuario usuario = autenticar(credenciales, request);
            usuarioService.marcarEntrada(usuario.getEmail());
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (BadCredentialsException badCredentialsException) {
            usuarioService.marcarCaptcha(credenciales.getEmail(), true);
            throw badCredentialsException;
        } catch (Exception e) {
            logger.error("Error inesperado al autenticar al usuario ", e);
            throw e;
        }
    }

    private Usuario autenticar(Credenciales credenciales, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credenciales.getEmail(), credenciales.getPassword());
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (Usuario)authentication.getPrincipal();
    }

    @RequestMapping(value = "/desautenticar", method = RequestMethod.POST)
    public ResponseEntity logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/actual", method = RequestMethod.GET)
    public ResponseEntity<Usuario> obtener(@AuthenticationPrincipal Usuario usuario) {
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/activar/{id}", method = RequestMethod.POST)
    // @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity<ErrorUnico> activarUsuario(@PathVariable("id") Asociado asociado, @RequestBody @Valid @Email String email, @AuthenticationPrincipal Usuario usuario) throws MessagingException {
        if (!asociado.isActivo())
            return new ResponseEntity<>(CodigoError.AsociadoInactivo.asError(), HttpStatus.BAD_REQUEST);
        if (usuarioService.existeActivacionEnCurso(email))
            return new ResponseEntity<>(CodigoError.ActivacionDeUsuarioEnCurso.asError(), HttpStatus.CONFLICT);
        asociado.setEmail(email);
        if (asociado.getUsuarioCreadoPorId() == null) {
            asociado.setUsuarioCreadoPorId(usuario.getId());
            asociado.setUsuarioCreadoPorNombre(usuario.getNombreCompleto());
        }
        asociadoRepository.save(asociado);
        usuarioService.resetPassword(asociado.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/desactivar/{id}", method = RequestMethod.POST)
    // @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity<ErrorUnico> desactivarUsuario(@PathVariable("id") Asociado asociado, @AuthenticationPrincipal Usuario usuario) {
        if (usuario.getId().equals(asociado.getId()))
            return new ResponseEntity<>(CodigoError.DeshabilitarUsuarioActual.asError(), HttpStatus.BAD_REQUEST);
        usuarioService.desactivarUsuario(asociado.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/lenguaje", method = RequestMethod.POST)
    public ResponseEntity cambiarIdioma(@RequestBody String codigo, @AuthenticationPrincipal Usuario usuario) {
        boolean codigoCorrecto = usuarioService.cambiarIdioma(usuario.getId(), codigo);

        if (!codigoCorrecto)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        // Al recargar la página, la ruta /actual no toca BBDD, saca el usuario del contexto actual
        // por lo que no contiene los cambios realizados. Rellenamos la propiedad con el nuevo codigo.
        usuario.setLenguaje(codigo);
        return new ResponseEntity(HttpStatus.OK);
    }
}

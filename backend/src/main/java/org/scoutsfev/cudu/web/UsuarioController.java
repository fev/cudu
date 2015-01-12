package org.scoutsfev.cudu.web;

import org.hibernate.validator.constraints.Email;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Credenciales;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.UsuarioService;
import org.scoutsfev.cudu.storage.AsociadoRepository;
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
        // TODO @RequestBody not null @Valid

        usuarioService.comprobarCaptcha(credenciales, request);

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credenciales.getEmail(), credenciales.getPassword());
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            usuarioService.marcarCaptcha(credenciales.getEmail(), false);
            return new ResponseEntity<>((Usuario)authentication.getPrincipal(), HttpStatus.OK);
        } catch (BadCredentialsException badCredentialsException) {
            usuarioService.marcarCaptcha(credenciales.getEmail(), true);
            throw badCredentialsException;
        }
    }

    @RequestMapping(value = "/desautenticar", method = RequestMethod.POST)
    public ResponseEntity logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/actual", method = RequestMethod.GET)
    public ResponseEntity<Usuario> obtener(@AuthenticationPrincipal Usuario usuario) {
        // TODO Integration test: si_el_usuario_no_esta_autenticado_devuelve_403_en_auth_check
        // TODO Integration test: si_el_usuario_esta_autenticado_devuelve_200_y_el_usuario_actual_en_auth_check
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/activar/{id}", method = RequestMethod.POST)
    // @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity activarUsuario(@PathVariable("id") Asociado asociado, @RequestBody @Valid @Email String email, @AuthenticationPrincipal Usuario usuario) throws MessagingException {
        if (!asociado.isActivo())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        asociado.setEmail(email);
        asociadoRepository.save(asociado);
        usuarioService.resetPassword(asociado.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }
}

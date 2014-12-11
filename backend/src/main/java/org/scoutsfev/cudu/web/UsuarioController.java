package org.scoutsfev.cudu.web;

import org.hibernate.validator.constraints.Email;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Token;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.UsuarioService;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;

@Controller
public class UsuarioController {

    private final AsociadoRepository asociadoRepository;
    private final UsuarioService usuarioService;
    private final TokenRepository tokenRepository;

    @Autowired
    public UsuarioController(AsociadoRepository asociadoRepository, UsuarioService usuarioService, TokenRepository tokenRepository) {
        this.asociadoRepository = asociadoRepository;
        this.usuarioService = usuarioService;
        this.tokenRepository = tokenRepository;
    }

    @RequestMapping(value = "/asociado/{id}/activarUsuario", method = RequestMethod.POST)
    // @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity activarUsuario(@PathVariable("id") Asociado asociado, @RequestBody @Valid @Email String email, @AuthenticationPrincipal Usuario usuario)
            throws MessagingException {
        if (!asociado.isActivo())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        asociado.setEmail(email);
        asociadoRepository.save(asociado);
        usuarioService.resetPassword(asociado.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/reset/{token}", method = RequestMethod.GET)
    public String resetForm(@PathVariable("token") Token token, Model model) throws NoSuchAlgorithmException {
        // TODO if (token == null || token.expirado(Instant.now())) return "redirect:/404";
        model.addAttribute("token", token);
        return "reset";
    }

    @RequestMapping(value = "/reset/{token}", method = RequestMethod.POST)
    public String reset(@Valid @ModelAttribute Token token, BindingResult result, Model model) {
        // TODO if (result.hasErrors()) Redirect a 400 Bad Request
        String codigoError = usuarioService.cambiarPassword(token);
        model.addAttribute("email", token.getEmail());
        if (codigoError == null)
            return "reset_ok";
        model.addAttribute("codigoError", codigoError);
        return "reset_error";
    }

    @RequestMapping(value="/resetnew", method = RequestMethod.GET)
    public @ResponseBody Token resetPlayground() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        String oneTimeCode = new BigInteger(130, secureRandom).toString(32);
        Duration duracion = Duration.ofSeconds(600);
        Token token = new Token("jack.sparrow@gmail.com", oneTimeCode, Instant.now(), duracion);
        tokenRepository.save(token);
        return token;
    }
}

package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Token;
import org.scoutsfev.cudu.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Controller
@RequestMapping("/reset")
public class ResetPasswordController {

    private final UsuarioService usuarioService;

    @Autowired
    public ResetPasswordController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public String resetForm(@PathVariable("token") Token token, Model model) throws NoSuchAlgorithmException {
        if (token == null || token.expirado(Instant.now()))
            return "forward:/404";
        model.addAttribute("token", token);
        return "reset";
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.POST)
    public String reset(@Valid @ModelAttribute Token token, BindingResult result, Model model) {
        // TODO if (result.hasErrors()) Redirect a 400 Bad Request
        String codigoError = usuarioService.cambiarPassword(token);
        model.addAttribute("email", token.getEmail());
        if (codigoError == null)
            return "reset_ok";
        model.addAttribute("codigoError", codigoError);
        return "reset_error";
    }

    /*@RequestMapping(value="/new", method = RequestMethod.GET)
    public @ResponseBody Token resetPlayground() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        String oneTimeCode = new BigInteger(130, secureRandom).toString(32);
        Duration duracion = Duration.ofSeconds(600);
        Token token = new Token("jack.sparrow@gmail.com", oneTimeCode, Instant.now(), duracion);
        tokenRepository.save(token);
        return token;
    }*/
}

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
    public String prepareResetForm(@PathVariable("token") Token token, Model model) throws NoSuchAlgorithmException {
        if (token == null || token.expirado(Instant.now()))
            return "forward:/404";
        model.addAttribute("token", token);
        model.addAttribute("score", 0);
        return "reset";
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.POST)
    public String submitPasswordReset(@Valid @ModelAttribute Token token, BindingResult result, Model model) {
        // TODO if (result.hasErrors()) Redirect a 400 Bad Request
        String codigoError = usuarioService.cambiarPassword(token);
        model.addAttribute("email", token.getEmail());
        if (codigoError == null)
            return "reset_ok";
        model.addAttribute("codigoError", codigoError);
        return "reset_error";
    }
}

package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public ResponseEntity<Usuario> obtener(@AuthenticationPrincipal Usuario usuario) {
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}

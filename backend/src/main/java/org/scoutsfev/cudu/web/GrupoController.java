package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class GrupoController {

    private final GrupoRepository grupoRepository;

    @Autowired
    public GrupoController(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @RequestMapping(value = "/grupo/{id}", method = RequestMethod.GET)
    public ResponseEntity<Grupo> obtener(@PathVariable("id") String grupoId) {
        Grupo grupo = grupoRepository.findOne(grupoId);
        return new ResponseEntity<>(grupo, HttpStatus.OK);
    }

    @RequestMapping(value = "/grupo/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Grupo> editar(@RequestBody Grupo editado, @PathVariable("id") Grupo original, @AuthenticationPrincipal Usuario usuario) {
        BeanUtils.copyProperties(editado, original, "asociados");
        return new ResponseEntity<Grupo>(grupoRepository.save(original), HttpStatus.OK);
    }
}

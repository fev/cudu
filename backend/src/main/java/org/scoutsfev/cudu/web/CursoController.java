package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.AmbitoEdicion;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lluerna/curso")
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public Page<Curso> listado(@AuthenticationPrincipal Usuario usuario, Pageable pageable) {
        return cursoService.listado(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public ResponseEntity<Curso> obtener(@PathVariable("cursoId") Integer cursoId, @AuthenticationPrincipal Usuario usuario) {
        Curso curso = cursoService.obtener(cursoId);
        if (curso == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public Curso crear(@RequestBody @Valid Curso curso, @AuthenticationPrincipal Usuario usuario) {
        return cursoService.guardar(curso);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public Curso editar(@RequestBody @Valid Curso editado, @PathVariable("id") Integer cursoId, @AuthenticationPrincipal Usuario usuario) {
        editado.setId(cursoId);
        return cursoService.guardar(editado);
    }

    @RequestMapping(value = "/{id}/publicar", method = RequestMethod.PUT)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void publicar(@PathVariable("id") Integer cursoId, @AuthenticationPrincipal Usuario usuario) {

    }

    @RequestMapping(value = "/{id}/ocultar", method = RequestMethod.PUT)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void ocultar(@PathVariable("id") Integer cursoId, @AuthenticationPrincipal Usuario usuario) {

    }
}

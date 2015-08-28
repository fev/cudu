package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @RequestMapping(value = "/{cursoId}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/{cursoId}", method = RequestMethod.PUT)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public Curso editar(@RequestBody @Valid Curso editado, @PathVariable("cursoId") Integer cursoId, @AuthenticationPrincipal Usuario usuario) {
        editado.setId(cursoId);
        return cursoService.guardar(editado);
    }

    // Eliminados, utilizar método 'editar', visible = true/false
    // @RequestMapping(value = "/{id}/publicar", method = RequestMethod.PUT)
    // @RequestMapping(value = "/{id}/ocultar", method = RequestMethod.PUT)

    @RequestMapping(value = "/{cursoId}/formadores", method = RequestMethod.POST)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void añadirFormador(@PathVariable("cursoId") Integer cursoId, @RequestBody @Valid @NotNull Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        cursoService.añadirFormador(cursoId, asociadoId);
    }

    @RequestMapping(value = "/{cursoId}/formadores/{asociadoId}", method = RequestMethod.DELETE)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void quitarFormador(@PathVariable("cursoId") Integer cursoId, @PathVariable("asociadoId") Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        cursoService.quitarFormador(cursoId, asociadoId);
    }

    @RequestMapping(value = "/{cursoId}/participantes", method = RequestMethod.POST)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void añadirParticipante(@PathVariable("cursoId") Integer cursoId, @RequestBody @Valid @NotNull Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        cursoService.añadirParticipante(cursoId, asociadoId);
    }

    @RequestMapping(value = "/{cursoId}/participantes/{asociadoId}", method = RequestMethod.DELETE)
    // @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void quitarParticipante(@PathVariable("cursoId") Integer cursoId, @PathVariable("asociadoId") Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        cursoService.quitarParticipante(cursoId, asociadoId);
    }
}

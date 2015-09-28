package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Curso;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.validadores.ValidadorInscripcionCurso;
import org.scoutsfev.cudu.services.AuthorizationService;
import org.scoutsfev.cudu.services.CursoService;
import org.scoutsfev.cudu.web.validacion.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Métodos relacionados con la gestión de cursos
 *
 * Todos los métodos requieren un perfil de técnico de la escuela,
 * a excepción de estos tres métodos que los utiliza la pantalla
 * para la inscripción de cursos disponible para cualquier asociado:
 * - listado de cursos activos    /lluerna/curso
 * - inscribirse en curso         POST /lluerna/curso/42/participantes  body es asociadoId
 * - cancelar inscripción         DELETE /lluerna/curso/42/participantes/39
 */
@RestController
@RequestMapping("/lluerna/curso")
public class CursoController {

    private final CursoService cursoService;
    private final AuthorizationService authorizationService;
    private final ValidadorInscripcionCurso validadorInscripcionCurso;

    @Autowired
    public CursoController(CursoService cursoService, AuthorizationService authorizationService, ValidadorInscripcionCurso validadorInscripcionCurso) {
        this.cursoService = cursoService;
        this.authorizationService = authorizationService;
        this.validadorInscripcionCurso = validadorInscripcionCurso;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Curso> listado(@AuthenticationPrincipal Usuario usuario, Pageable pageable) {
        return cursoService.listado(pageable, usuario.getId());
    }

    @RequestMapping(value = "/completo", method = RequestMethod.GET)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public Page<Curso> listadoCompleto(@AuthenticationPrincipal Usuario usuario, Pageable pageable) {
        return cursoService.listadoCompleto(pageable);
    }

    @RequestMapping(value = "/{cursoId}", method = RequestMethod.GET)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public ResponseEntity<Curso> obtener(@PathVariable("cursoId") Integer cursoId, @AuthenticationPrincipal Usuario usuario) {
        Curso curso = cursoService.obtener(cursoId);
        if (curso == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public Curso crear(@RequestBody @Valid Curso curso, @AuthenticationPrincipal Usuario usuario) {
        return cursoService.guardar(curso);
    }

    @RequestMapping(value = "/{cursoId}", method = RequestMethod.PUT)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public Curso editar(@RequestBody @Valid Curso editado, @PathVariable("cursoId") Integer cursoId, @AuthenticationPrincipal Usuario usuario) {
        editado.setId(cursoId);
        return cursoService.guardar(editado);
    }

    @RequestMapping(value = "/{cursoId}/formadores", method = RequestMethod.POST)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void añadirFormador(@PathVariable("cursoId") Integer cursoId, @RequestBody @Valid @NotNull Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        cursoService.añadirFormador(cursoId, asociadoId);
    }

    @RequestMapping(value = "/{cursoId}/formadores/{asociadoId}", method = RequestMethod.DELETE)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void quitarFormador(@PathVariable("cursoId") Integer cursoId, @PathVariable("asociadoId") Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        cursoService.quitarFormador(cursoId, asociadoId);
    }

    @RequestMapping(value = "/{cursoId}/participantes", method = RequestMethod.POST)
    public ResponseEntity añadirParticipante(@PathVariable("cursoId") Curso curso, @RequestBody @Valid @NotNull Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        if (authorizationService.puedeAccederLluerna(usuario)) {
            return ok(cursoService.añadirParticipante(curso.getId(), asociadoId));
        }
        List<Error> erroresValidacion = validadorInscripcionCurso.validar(curso, LocalDateTime.now());
        if (erroresValidacion.size() > 0) {
            return badRequest().body(erroresValidacion);
        }
        // Si el usuario no es un técnico de lluerna sólo podemos inscribir o desinscribir el usuario actual
        return ok(cursoService.añadirParticipante(curso.getId(), usuario.getId()));
    }

    @RequestMapping(value = "/{cursoId}/participantes/{asociadoId}", method = RequestMethod.DELETE)
    public ResponseEntity quitarParticipante(@PathVariable("cursoId") Curso curso, @PathVariable("asociadoId") Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        if (authorizationService.puedeAccederLluerna(usuario)) {
            return ok(cursoService.quitarParticipante(curso.getId(), asociadoId));
        }
        List<Error> erroresValidacion = validadorInscripcionCurso.validar(curso, LocalDateTime.now());
        if (erroresValidacion.size() > 0) {
            return badRequest().body(erroresValidacion);
        }
        return ok(cursoService.quitarParticipante(curso.getId(), usuario.getId()));
    }
}

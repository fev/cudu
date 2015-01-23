package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Actividad;
import org.scoutsfev.cudu.domain.EventosAuditoria;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController()
public class ActividadesController {

    private final ActividadRepository actividadRepository;
    private final Validator validator;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ActividadesController(ActividadRepository actividadRepository, Validator validator, ApplicationEventPublisher eventPublisher) {
        this.actividadRepository = actividadRepository;
        this.validator = validator;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(value = "/actividad", method = RequestMethod.GET)
    public List<Actividad> obtenerLista(@AuthenticationPrincipal Usuario usuario) {
        return actividadRepository.findByGrupoIdAndFechaBajaIsNull(usuario.getGrupo().getId());
    }

    @RequestMapping(value = "/actividad/{id}", method = RequestMethod.GET)
    public ResponseEntity<Actividad> obtenerDetalle(@PathVariable Integer id, @AuthenticationPrincipal Usuario usuario) {
        Actividad actividad = actividadRepository.findOne(id);
        if (actividad == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (actividad.getFechaBaja() != null)
            return new ResponseEntity<>(HttpStatus.GONE);
        if (usuario.getGrupo() == null || !actividad.getGrupoId().equals(usuario.getGrupo().getId())) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "GET /actividad/" + id));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(actividad, HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Actividad> crear(@RequestBody @Valid Actividad actividad, @AuthenticationPrincipal Usuario usuario) {
        if (usuario.getGrupo() == null || !actividad.getGrupoId().equals(usuario.getGrupo().getId())) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "POST /actividad"));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        actividad.setId(null);
        actividad.setCreadaPor(usuario.getNombreCompleto());
        Actividad guardada = actividadRepository.save(actividad);
        return new ResponseEntity<>(guardada, HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Actividad> editar(@RequestBody @Valid Actividad editada, @PathVariable("id") Actividad original, @AuthenticationPrincipal Usuario usuario) {
        if (usuario.getGrupo() == null || !editada.getGrupoId().equals(usuario.getGrupo().getId())) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "PUT /actividad/" + editada.getId()));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        editada.setId(original.getId());
        editada.setGrupoId(original.getGrupoId());
        editada.setCreadaPor(original.getCreadaPor());
        Actividad guardada = actividadRepository.save(editada);
        return new ResponseEntity<>(guardada, HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad/id", method = RequestMethod.DELETE)
    public ResponseEntity eliminar(@PathVariable Integer id, @AuthenticationPrincipal Usuario usuario) {
        Actividad actividad = actividadRepository.findOne(id);
        if (usuario.getGrupo() == null || !actividad.getGrupoId().equals(usuario.getGrupo().getId())) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "DELETE /actividad/" + id));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        actividad.setFechaBaja(Timestamp.valueOf(LocalDateTime.now()));
        actividadRepository.save(actividad);
        return new ResponseEntity(HttpStatus.OK);
    }
}

package org.scoutsfev.cudu.web;

import com.google.common.base.Strings;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.domain.dto.ActividadDetalleDto;
import org.scoutsfev.cudu.domain.dto.ActividadDetalleDtoPk;
import org.scoutsfev.cudu.storage.ActividadRepository;
import org.scoutsfev.cudu.storage.dto.ActividadDetalleDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ActividadesController {

    private final ActividadRepository actividadRepository;
    private final ActividadDetalleDtoRepository actividadDetalleDtoRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ActividadesController(ActividadRepository actividadRepository, ActividadDetalleDtoRepository actividadDetalleDtoRepository, ApplicationEventPublisher eventPublisher) {
        this.actividadRepository = actividadRepository;
        this.actividadDetalleDtoRepository = actividadDetalleDtoRepository;
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
        if (!laActividadPerteneceAlGrupoDelUsuario(actividad, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "GET /actividad/" + id));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<ActividadDetalleDto> detalle = actividadDetalleDtoRepository.findByActividadId(actividad.getId());
        actividad.setDetalle(detalle);
        return new ResponseEntity<>(actividad, HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Actividad> crear(@RequestBody @Valid Actividad actividad, @AuthenticationPrincipal Usuario usuario) {
        if (!laActividadPerteneceAlGrupoDelUsuario(actividad, usuario)) {
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
        if (!laActividadPerteneceAlGrupoDelUsuario(editada, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "PUT /actividad/" + editada.getId()));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        editada.setId(original.getId());
        editada.setGrupoId(original.getGrupoId());
        editada.setCreadaPor(original.getCreadaPor());
        Actividad guardada = actividadRepository.save(editada);
        return new ResponseEntity<>(guardada, HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad/{id}", method = RequestMethod.DELETE)
    public ResponseEntity eliminar(@PathVariable("id") Actividad actividad, @AuthenticationPrincipal Usuario usuario) {
        if (!laActividadPerteneceAlGrupoDelUsuario(actividad, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "DELETE /actividad/" + actividad.getId()));
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        actividad.setFechaBaja(Timestamp.valueOf(LocalDateTime.now()));
        actividadRepository.save(actividad);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad/{id}/asociado/{asociadoId}", method = RequestMethod.POST)
    public ResponseEntity<ActividadDetalleDto> añadirAsistente(@PathVariable("id") Actividad actividad, @PathVariable Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        if (!laActividadPerteneceAlGrupoDelUsuario(actividad, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "añadirAsistente"));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        actividadRepository.añadirAsistente(actividad.getId(), asociadoId);
        ActividadDetalleDto asistente = actividadDetalleDtoRepository.findOne(new ActividadDetalleDtoPk(actividad.getId(), asociadoId));
        return new ResponseEntity<>(asistente, HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad/{id}/rama", method = RequestMethod.POST)
    public ResponseEntity<Actividad> añadirRama(@RequestBody Rama rama, @PathVariable("id") Actividad actividad, @AuthenticationPrincipal Usuario usuario) {
        if (!laActividadPerteneceAlGrupoDelUsuario(actividad, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "añadirAsistente"));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        actividadRepository.añadirRamaCompleta(actividad.getId(), rama.isColonia(), rama.isManada(), rama.isExploradores(), rama.isExpedicion(), rama.isRuta());
        return obtenerDetalle(actividad.getId(), usuario);
    }

    @RequestMapping(value = "/actividad/{id}/asociado/{asociadoId}", method = RequestMethod.DELETE)
    public ResponseEntity eliminarAsistente(@PathVariable("id") Actividad actividad, @PathVariable Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        if (!laActividadPerteneceAlGrupoDelUsuario(actividad, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "eliminarAsistente"));
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        actividadRepository.eliminarAsistente(actividad.getId(), asociadoId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/actividad/{id}/asociado/{asociadoId}/estado", method = RequestMethod.POST)
    public ResponseEntity<String> cambiarEstadoAsistente(@RequestBody String estadoAsistente, @PathVariable("id") Actividad actividad,
                                                         @PathVariable Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        if (!laActividadPerteneceAlGrupoDelUsuario(actividad, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "cambiarEstadoAsistente"));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<EstadoAsistente> estado = parsearEstadoAsistente(estadoAsistente);
        if (!estado.isPresent())
            return new ResponseEntity<>("No es posible parsear el estado.", HttpStatus.BAD_REQUEST);
        actividadRepository.cambiarEstadoAsistente(actividad.getId(), asociadoId, estado.get().getEstado());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Optional<EstadoAsistente> parsearEstadoAsistente(String valor) {
        if (Strings.isNullOrEmpty(valor) || valor.length() != 1)
            return Optional.empty();
        return EstadoAsistente.tryParse(valor.charAt(0));
    }

    private boolean laActividadPerteneceAlGrupoDelUsuario(Actividad actividad, Usuario usuario) {
        return actividad != null && usuario != null && usuario.getGrupo() != null
            && actividad.getGrupoId().equals(usuario.getGrupo().getId());
    }
}

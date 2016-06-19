package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.EventosAuditoria;
import org.scoutsfev.cudu.domain.Grupo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.AuthorizationService;
import org.scoutsfev.cudu.storage.GrupoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GrupoController {

    private final GrupoRepository grupoRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthorizationService authorizationService;

    @Autowired
    public GrupoController(GrupoRepository grupoRepository, ApplicationEventPublisher eventPublisher, AuthorizationService authorizationService) {
        this.grupoRepository = grupoRepository;
        this.eventPublisher = eventPublisher;
        this.authorizationService = authorizationService;
    }

    @RequestMapping(value = "/grupo/all", method = RequestMethod.GET)
    public Page<Grupo> listado(@AuthenticationPrincipal Usuario usuario, Pageable pageable) {
        return grupoRepository.findAll(pageable);
    }

    @RequestMapping(value = "/grupo/{id}", method = RequestMethod.GET)
    public ResponseEntity<Grupo> obtener(@PathVariable("id") String grupoId, @AuthenticationPrincipal Usuario usuario) {
        Grupo grupo = grupoRepository.findOne(grupoId);
        if (!authorizationService.comprobarAccesoGrupo(grupo, usuario, false)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "GET /grupo/" + grupoId));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (grupo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(grupo, HttpStatus.OK);
    }

    @RequestMapping(value = "/grupo/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Grupo> editar(@RequestBody @Valid Grupo editado, @PathVariable("id") Grupo original, @AuthenticationPrincipal Usuario usuario) {
        if (!authorizationService.comprobarAccesoGrupo(original, usuario, true)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "PUT /grupo/" + editado.getId()));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        BeanUtils.copyProperties(editado, original, "asociados");
        return new ResponseEntity<>(grupoRepository.save(original), HttpStatus.OK);
    }
}

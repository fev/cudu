package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.scoutsfev.cudu.domain.Asociacion;
import org.scoutsfev.cudu.domain.EventosAuditoria;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.dto.EditarLiquidacionDto;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;
import org.scoutsfev.cudu.domain.dto.LiquidacionDesgloseDto;
import org.scoutsfev.cudu.services.AuthorizationService;
import org.scoutsfev.cudu.storage.Borradores;
import org.scoutsfev.cudu.storage.LiquidacionesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.scoutsfev.cudu.web.utils.HttpServletRequestExtensions.auditUrl;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/liquidaciones")
public class LiquidacionController {

    private final LiquidacionesStorage storage;
    private final AuthorizationService authorizationService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public LiquidacionController(LiquidacionesStorage storage, AuthorizationService authorizationService, ApplicationEventPublisher eventPublisher) {
        this.storage = storage;
        this.authorizationService = authorizationService;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(value = "/grupos/{rondaId}", method = RequestMethod.GET)
    public ResponseEntity<List<LiquidacionGrupos>> resumenPorGrupos(@PathVariable("rondaId") Short rondaId, HttpServletRequest request, @AuthenticationPrincipal Usuario usuario) {
        if (!authorizationService.esTecnico(usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, auditUrl(request)));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Asociacion restriccionAsociacion = usuario.getRestricciones().getRestriccionAsociacion();
        return ok(storage.resumenPorGrupos(rondaId, restriccionAsociacion));
    }

    @RequestMapping(value = "/{liquidacionId}", method = RequestMethod.GET)
    public ResponseEntity<LiquidacionDesgloseDto> desglose(@PathVariable("liquidacionId") int liquidacionId, HttpServletRequest request, @AuthenticationPrincipal Usuario usuario) {
        Optional<ResponseEntity<LiquidacionDesgloseDto>> erroresVerificacion = VerificarAccesoLiquidacion(liquidacionId, usuario, auditUrl(request));
        if (erroresVerificacion.isPresent())
            return erroresVerificacion.get();
        return ok(storage.desglose(liquidacionId));
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}", method = RequestMethod.GET)
    public ResponseEntity<LiquidacionBalanceDto> balanceGrupo(@PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo, HttpServletRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        if (!authorizationService.puedeEditarUsuariosDelGrupo(grupoId, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, auditUrl(request)));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ok(this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario));
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}", method = RequestMethod.POST)
    public ResponseEntity<LiquidacionBalanceDto> crear(@PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo, HttpServletRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        if (!authorizationService.puedeEditarUsuariosDelGrupo(grupoId, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, auditUrl(request)));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        storage.crear(grupoId, rondaId, usuario.getUsername());
        return ok(this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario));
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}/{liquidacionId}", method = RequestMethod.DELETE)
    public ResponseEntity<LiquidacionBalanceDto> eliminar(
            @PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId, @PathVariable("liquidacionId") int liquidacionId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo, HttpServletRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        Optional<ResponseEntity<LiquidacionBalanceDto>> erroresVerificacion = VerificarAccesoLiquidacion(liquidacionId, usuario, auditUrl(request));
        if (erroresVerificacion.isPresent())
            return erroresVerificacion.get();
        storage.eliminar(liquidacionId);
        return ok(this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario));
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}/{liquidacionId}", method = RequestMethod.PUT)
    public ResponseEntity<LiquidacionBalanceDto> editar(
            @PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId, @PathVariable("liquidacionId") int liquidacionId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo, HttpServletRequest request,
            @RequestBody @Valid EditarLiquidacionDto editada, @AuthenticationPrincipal Usuario usuario) {
        Optional<ResponseEntity<LiquidacionBalanceDto>> erroresVerificacion = VerificarAccesoLiquidacion(liquidacionId, usuario, auditUrl(request));
        if (erroresVerificacion.isPresent())
            return erroresVerificacion.get();
        storage.editar(liquidacionId, editada.ajusteManual, editada.pagado, editada.borrador);
        return ok(this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario));
    }

    private LiquidacionBalanceDto balanceDeGrupo(String grupoId, Short rondaId, Boolean incluirBorradoresEnCalculo, Usuario usuario) {
        boolean borradores = incluirBorradoresEnCalculo == null ? false : incluirBorradoresEnCalculo;
        boolean usuarioEsTecnico = authorizationService.esTecnico(usuario);
        if (!usuarioEsTecnico)
            return storage.balanceGrupo(grupoId, rondaId, Borradores.Ocultar);
        else
            return storage.balanceGrupo(grupoId, rondaId, borradores ? Borradores.MostrarCalculando : Borradores.MostrarSinCalcular);
    }

    private <T> Optional<ResponseEntity<T>> VerificarAccesoLiquidacion(int liquidacionId, Usuario usuario, String path) {
        String grupoId = storage.grupoDeLaLiquidacion(liquidacionId);
        if (grupoId == null) {
            return Optional.of(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        if (!authorizationService.puedeEditarUsuariosDelGrupo(grupoId, usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, path));
            return Optional.of(new ResponseEntity<>(HttpStatus.FORBIDDEN));
        }
        return Optional.empty();
    }
}

package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.domain.dto.CambiarRamaDto;
import org.scoutsfev.cudu.domain.dto.CambiarTipoDto;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.web.utils.ResponseEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RestController
public class AsociadoController {

    private final AsociadoRepository asociadoRepository;
    private final CacheManager cacheManager;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AsociadoController(AsociadoRepository asociadoRepository, CacheManager cacheManager, ApplicationEventPublisher eventPublisher) {
        this.asociadoRepository = asociadoRepository;
        this.cacheManager = cacheManager;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(value = "/asociado", method = RequestMethod.GET)
    public Page<Asociado> listado(@AuthenticationPrincipal Usuario usuario, Pageable pageable) {
        // TODO Si el usuario es FEV o Lluerna, devuelve todos
        // TODO Si el usuario es SdC, SdA o MEV, devuelve solo el subconjunto
        // TODO Si el usuario es de grupo pero no tiene permiso, denegar (esta con anotación).
        String idGrupo = usuario.getGrupo().getId();
        return asociadoRepository.findByGrupoId(idGrupo, pageable);
    }

    @RequestMapping(value = "/asociado/{id}", method = RequestMethod.GET)
    @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity<Asociado> obtener(@PathVariable Integer id, @AuthenticationPrincipal Usuario usuario) {
        Asociado asociado = asociadoRepository.findByIdAndFetchCargosEagerly(id);
        if (asociado == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }

    @RequestMapping(value = "/asociado", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Asociado crear(@RequestBody @Valid Asociado asociado, @AuthenticationPrincipal Usuario usuario) {
        asociado.setId(null);
        asociado.setUsuarioActivo(false);
        asociado.setAmbitoEdicion(AmbitoEdicion.Grupo);
        asociado.setGrupoId(usuario.getGrupo().getId());
        descartarCacheGraficas(asociado.getGrupoId());
        return asociadoRepository.save(asociado);
    }

    @RequestMapping(value = "/asociado/{id}", method = RequestMethod.PUT)
    public Asociado editar(@RequestBody @Valid Asociado editado, @PathVariable("id") Asociado original, @AuthenticationPrincipal Usuario usuario) {
        editado.setGrupoId(original.getGrupoId());
        editado.setUsuarioActivo(original.isUsuarioActivo());
        editado.setAmbitoEdicion(original.getAmbitoEdicion());
        editado.setUsuarioActivo(original.isUsuarioActivo());
        if (editado.isActivo() != original.isActivo())
            descartarCacheGraficas(editado.getGrupoId());
        return asociadoRepository.save(editado);
    }

    @RequestMapping(value = "/asociado/{id}/activar", method = RequestMethod.PUT)
    @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public void activar(@PathVariable("id") Integer id, @AuthenticationPrincipal Usuario usuario) {
        asociadoRepository.activar(id, true);
    }

    @RequestMapping(value = "/asociado/{id}/desactivar", method = RequestMethod.PUT)
    @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public void desactivar(@PathVariable("id") Integer id, @AuthenticationPrincipal Usuario usuario) {
        asociadoRepository.activar(id, false);
    }

    @RequestMapping(value = "/asociado/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public void eliminar(@PathVariable("id") Integer id, @AuthenticationPrincipal Usuario usuario) {
        // TODO No puedes eliminarte
        // TODO Integridad referencial (cargos OK, revisar actividades)
        asociadoRepository.delete(id);
    }

    @RequestMapping(value = "/asociado/cambiarRama", method = RequestMethod.POST)
    public void cambiarRama(@RequestBody @Valid CambiarRamaDto dto, @AuthenticationPrincipal Usuario usuario) {
        // Si el usuario tienen no_puede_editar_otras_ramas, no puede cambiar su rama
        // De forma normal al editar un usuario se hace dicha comprobación, y en este
        // caso debemos hacer la de nuevo. Simplemente lo eliminados de la lista.
        if (usuario.getRestricciones().isNoPuedeEditarOtrasRamas())
            dto.asociados.removeIf(id -> id.equals(usuario.getId()));
        String grupoId = usuario.getGrupo().getId();
        asociadoRepository.cambiarRama(dto.asociados, dto.rama, grupoId);
        descartarCacheGraficas(grupoId);
    }

    @RequestMapping(value = "/asociado/cambiarTipo", method = RequestMethod.POST)
    public ResponseEntity<String> cambiarTipo(@RequestBody @Valid CambiarTipoDto dto, @AuthenticationPrincipal Usuario usuario) {
        if (dto.tipo != TipoAsociado.Joven && dto.tipo != TipoAsociado.Kraal && dto.tipo != TipoAsociado.Comite) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "POST /asociado/cambiarTipo"));
            return ResponseEntityFactory.forbidden("El tipo no es correcto. Permitidos para el usuario actual: [J, K, C].");
        }
        String grupoId = usuario.getGrupo().getId();
        asociadoRepository.cambiarTipo(dto.asociados, dto.tipo, grupoId);
        descartarCacheGraficas(grupoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/asociado/desactivar", method = RequestMethod.POST)
    public void desactivar(@RequestBody ArrayList<Integer> asociados, @AuthenticationPrincipal Usuario usuario) {
        String grupoId = usuario.getGrupo().getId();
        asociadoRepository.desactivar(asociados, grupoId);
        descartarCacheGraficas(grupoId);
    }

    private void descartarCacheGraficas(String grupoId) {
        cacheManager.getCache(CacheKeys.DatosGraficasGlobales).clear();
        if (grupoId != null) {
            cacheManager.getCache(CacheKeys.DatosGraficasPorGrupoRama).evict(grupoId);
            cacheManager.getCache(CacheKeys.DatosGraficasPorGrupoTipo).evict(grupoId);
        }
    }
}

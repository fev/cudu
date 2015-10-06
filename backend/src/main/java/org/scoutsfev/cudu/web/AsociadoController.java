package org.scoutsfev.cudu.web;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.domain.dto.CambiarRamaDto;
import org.scoutsfev.cudu.domain.dto.CambiarTipoDto;
import org.scoutsfev.cudu.domain.validadores.ImpresionTabla;
import org.scoutsfev.cudu.services.FichaService;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.web.utils.ResponseEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@RestController
public class AsociadoController {

    private final AsociadoRepository asociadoRepository;
    private final CacheManager cacheManager;
    private final FichaService fichaService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AsociadoController(AsociadoRepository asociadoRepository, FichaService fichaService, CacheManager cacheManager, ApplicationEventPublisher eventPublisher) {
        this.asociadoRepository = asociadoRepository;
        this.fichaService = fichaService;
        this.cacheManager = cacheManager;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(value = "/asociado/imprimir", method = RequestMethod.POST)
    public RespuestaFichero GenerarListado(
            @RequestBody ImpresionTabla impresionTabla,
            @AuthenticationPrincipal Usuario usuario) throws IOException, COSVisitorException {

        String archivo = fichaService.GenerarListado(impresionTabla.getIdentificadores(), impresionTabla.getColumnas(), usuario);

        Path path = Paths.get(archivo);
        RespuestaFichero respuesta = new RespuestaFichero();
        respuesta.setNombre(path.getFileName().toString());

        return respuesta;
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
    @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
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

    // http -v POST http://localhost:8080/asociado/cambiarRama Cookie:JSESSIONID=... asociados:=[18158,18159,18485] rama:='{ "colonia": true }'
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

    // http -v POST http://localhost:8080/asociado/cambiarTipo Cookie:JSESSIONID=... asociados:=[18158,18159,18485] tipo=K
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

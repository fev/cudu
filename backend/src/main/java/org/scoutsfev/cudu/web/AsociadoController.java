package org.scoutsfev.cudu.web;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.domain.dto.CambiarRamaDto;
import org.scoutsfev.cudu.domain.dto.CambiarTipoDto;
import org.scoutsfev.cudu.domain.validadores.ImpresionTabla;
import org.scoutsfev.cudu.services.AuthorizationService;
import org.scoutsfev.cudu.services.FichaService;
import org.scoutsfev.cudu.services.UsuarioService;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.AsociadoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import org.scoutsfev.cudu.domain.Token;

import static org.scoutsfev.cudu.storage.especificaciones.EspecificacionesAsociado.PorGrupoSegunRama;
import static org.scoutsfev.cudu.web.utils.ResponseEntityFactory.forbidden;

@RestController
public class AsociadoController {

    private final AsociadoRepository asociadoRepository;
    private final CacheManager cacheManager;
    private final AsociadoStorage asociadoStorage;
    private final FichaService fichaService;
    private final AuthorizationService authorizationService;
    private final ApplicationEventPublisher eventPublisher;
    private final UsuarioService usuarioService;

    @Autowired
    public AsociadoController(AsociadoRepository asociadoRepository, AsociadoStorage asociadoStorage,
            FichaService fichaService, CacheManager cacheManager, AuthorizationService authorizationService,
            ApplicationEventPublisher eventPublisher, UsuarioService usuarioService) {
        this.asociadoRepository = asociadoRepository;
        this.asociadoStorage = asociadoStorage;
        this.fichaService = fichaService;
        this.cacheManager = cacheManager;
        this.authorizationService = authorizationService;
        this.eventPublisher = eventPublisher;
        this.usuarioService = usuarioService;
    }

    @RequestMapping(value = "/asociado/imprimir", method = RequestMethod.POST)
    public RespuestaFichero GenerarListado(
            @RequestBody ImpresionTabla impresionTabla,
            @AuthenticationPrincipal Usuario usuario) throws IOException, COSVisitorException {

        String archivo = fichaService.GenerarListado(impresionTabla.getIdentificadores(), impresionTabla.getColumnas(), impresionTabla.getTitulo(), usuario);

        Path path = Paths.get(archivo);
        RespuestaFichero respuesta = new RespuestaFichero();
        respuesta.setNombre(path.getFileName().toString());

        return respuesta;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/asociado", method = RequestMethod.GET)
    public Page<Asociado> listado(@AuthenticationPrincipal Usuario usuario, Pageable pageable) {
        if (usuario.getGrupo()==null)
          return null;
        String grupoId = usuario.getGrupo().getId();
        if (usuario.getRestricciones().isNoPuedeEditarOtrasRamas()) {
            Specification<Asociado> porGrupoSegunRama = PorGrupoSegunRama(usuario.getId(), grupoId, usuario.isRamaColonia(), usuario.isRamaManada(), usuario.isRamaExploradores(), usuario.isRamaExpedicion(), usuario.isRamaRuta());
            return asociadoRepository.findAll(porGrupoSegunRama, pageable);
        }
        return asociadoRepository.findByGrupoIdOrderByActivoDesc(grupoId, pageable);
    }

    @RequestMapping(value = "/tecnico/asociado", method = RequestMethod.GET)
    public ResponseEntity listadoTecnico(
            @RequestParam(required = false) Asociacion asociacion,
            @RequestParam(required = false) String grupoId,
            @RequestParam(required = false) TipoAsociado tipo,
            @RequestParam(required = false) String ramasSeparadasPorComas,
            @RequestParam(required = false) Boolean inactivo,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) String nombreApellido,
            @RequestParam(required = false) String orden,
            @RequestParam(required = false) Boolean ordenAsc,
            @RequestParam(required = false) Boolean certificadoDelitosSexuales,
            @AuthenticationPrincipal Usuario usuario,
            Pageable pageable) {

        if (!authorizationService.esTecnico(usuario)) {
            eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.AccesoDenegado, "GET /tecnico/asociado"));
            return forbidden("El usuario no es técnico federativo o asociativo.");
        }

        Asociacion restriccionAsociacion = usuario.getRestricciones().getRestriccionAsociacion();
        if (restriccionAsociacion != null)
            asociacion = restriccionAsociacion;

        List<String> ramas = null;
        if (!Strings.isNullOrEmpty(ramasSeparadasPorComas)) {
            ramas = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split(ramasSeparadasPorComas));
        }

        SparseTable listado = asociadoStorage.listado(asociacion, grupoId, tipo, ramas, inactivo, sexo, nombreApellido, orden, ordenAsc, certificadoDelitosSexuales, pageable);
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @RequestMapping(value = "/asociado/{id}", method = RequestMethod.GET)
    @PreAuthorize("@auth.puedeVerAsociado(#id, #usuario)")
    public ResponseEntity<Asociado> obtener(@PathVariable Integer id, @AuthenticationPrincipal Usuario usuario) {
        Asociado asociado = asociadoRepository.findByIdAndFetchCargosEagerly(id);
        if (asociado == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }


    /*
    * La consulta esActivo es permitida para apiKeys válidas. Si no, responde HTTP code 403, forbidden.
    *
    * Responde si un asociado (identificado por el dni) está en la base de datos de
    * cudu y está activo. Consulta los campos dni y activo del modelo Asociado.
    * Si hay más de un asociado con el mismo dni recoge el primero de ellos.
    * La consulta se hace con el método GET: api/asociado/esactivo/ZZZZ?q=XXXX , siendo XXXX el dni a buscar y ZZZZ una apikey válida.
    * Si existe un asociado con el dni y está activo, responde "true".
    * Si el asociado no está activo, o si no existe ningún asociado con el dni, responde "false".
    * Si la queryString pregunta por un atributo que no es 'q', devuleve HTTP code 400, bad request
    *
    */
    @RequestMapping(value = "/asociado/esactivo/{token}", method = RequestMethod.GET)
    public ResponseEntity<String> esActivo(@PathVariable("token") Token token, Model model, @RequestParam(required = true, value="q") List<String> dniLista) {
        if (token == null || token.expirado(Instant.now()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        String codigoError =usuarioService.nuevaApikeyValidacion(token);
        if (codigoError != null){
           return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Integer> ids =asociadoRepository.getIdFromDni(dniLista.get(0));
        if (ids == null || ids.isEmpty())
            return new ResponseEntity<>(new String("false") ,HttpStatus.OK);
        Boolean esActivo= asociadoRepository.esAsociadoActivo(dniLista.get(0));
        return new ResponseEntity<>(String.valueOf(esActivo), HttpStatus.OK);
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
    @PreAuthorize("@auth.puedeEditarAsociado(#original, #usuario)")
    public Asociado editar(@RequestBody @Valid Asociado editado, @PathVariable("id") Asociado original, @AuthenticationPrincipal Usuario usuario) {
        editado.setId(original.getId());
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
            return forbidden("El tipo no es correcto. Permitidos para el usuario actual: [J, K, C].");
        }
        String grupoId = usuario.getGrupo().getId();
        asociadoRepository.cambiarTipo(dto.asociados, dto.tipo, grupoId);
        descartarCacheGraficas(grupoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/asociado/desactivar", method = RequestMethod.POST)
    public void desactivarMultiples(@RequestBody ArrayList<Integer> asociados, @AuthenticationPrincipal Usuario usuario) {
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

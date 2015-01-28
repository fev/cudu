package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.AsociadoRepository;
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
public class AsociadoController {

    private final AsociadoRepository asociadoRepository;

    @Autowired
    public AsociadoController(AsociadoRepository asociadoRepository) {
        this.asociadoRepository = asociadoRepository;
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
    // @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity<Asociado> obtener(@PathVariable Integer id, @AuthenticationPrincipal Usuario usuario) {
        Asociado asociado = asociadoRepository.findByIdAndFetchCargosEagerly(id);
        if (asociado == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }

    @RequestMapping(value = "/asociado", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public Asociado crear(@RequestBody @Valid Asociado asociado, @AuthenticationPrincipal Usuario usuario) {
        asociado.setId(null);
        return asociadoRepository.save(asociado);
    }

    @RequestMapping(value = "/asociado/{id}", method = RequestMethod.PUT)
    // @PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public Asociado editar(@RequestBody @Valid Asociado editado, @PathVariable("id") Asociado original, @AuthenticationPrincipal Usuario usuario) {
        editado.setGrupoId(original.getGrupoId());
        editado.setUsuarioActivo(original.isUsuarioActivo());
        editado.setAmbitoEdicion(original.getAmbitoEdicion());
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
}

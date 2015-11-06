package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto;
import org.scoutsfev.cudu.domain.dto.FormadorDto;
import org.scoutsfev.cudu.domain.dto.MiembroEscuelaDto;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.dto.MiembroEscuelaDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class TypeaheadController {

    private final AsociadoRepository asociadoRepository;

    @Autowired
    public TypeaheadController(AsociadoRepository asociadoRepository) {
        this.asociadoRepository = asociadoRepository;
    }

    @RequestMapping(value = "/typeahead/asociado/{query}", method = RequestMethod.GET)
    public Page<AsociadoTypeaheadDto> asociados(@PathVariable String query, @AuthenticationPrincipal Usuario usuario) {
        return asociadoRepository.typeahead(usuario.getGrupo().getId(), query.toLowerCase(), new PageRequest(0, 30));
    }

    @RequestMapping(value = "/typeahead/miembro/{query}", method = RequestMethod.GET)
    public Page<AsociadoTypeaheadDto> miembros(@PathVariable String query, @AuthenticationPrincipal Usuario usuario) {
        return asociadoRepository.typeahead(query.toLowerCase(), new PageRequest(0, 30));
    }

    @RequestMapping(value = "/typeahead/curso/{cursoId}/participante/{query}", method = RequestMethod.GET)
    public Page<AsociadoTypeaheadDto> participantes(@PathVariable String query, @PathVariable int cursoId, @AuthenticationPrincipal Usuario usuario) {
        return asociadoRepository.participanteTypeAhead(query.toLowerCase(), cursoId, new PageRequest(0, 30));
    }

    @RequestMapping(value = "/typeahead/formador/{query}", method = RequestMethod.GET)
    public Page<FormadorDto> formadores(@PathVariable String query, @AuthenticationPrincipal Usuario usuario) {
        return asociadoRepository.formadorTypeAhead(query.toLowerCase(), new PageRequest(0, 30));
    }
}

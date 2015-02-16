package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.dto.AsociadoTypeaheadDto;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}

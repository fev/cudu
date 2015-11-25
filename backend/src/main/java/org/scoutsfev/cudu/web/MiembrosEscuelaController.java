package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.dto.MiembroEscuelaDto;
import org.scoutsfev.cudu.domain.dto.MiembroEscuelaEdicionDto;
import org.scoutsfev.cudu.services.LluernaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lluerna/miembro")
public class MiembrosEscuelaController {

    private final LluernaService lluernaService;

    @Autowired
    public MiembrosEscuelaController(LluernaService lluernaService) {
        this.lluernaService = lluernaService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public List<MiembroEscuelaDto> listado(@AuthenticationPrincipal Usuario usuario) {
        return lluernaService.obtenerMiembros();
    }

    @RequestMapping(value = "/{asociadoId}", method = RequestMethod.PUT)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void añadir(@PathVariable Integer asociadoId, @RequestBody MiembroEscuelaEdicionDto edicionMiembroDto, @AuthenticationPrincipal Usuario usuario) {
        lluernaService.añadirMiembro(asociadoId, edicionMiembroDto.getCargo(), edicionMiembroDto.isMesaPedagogica());
    }

    @RequestMapping(value = "/{asociadoId}", method = RequestMethod.DELETE)
    @PreAuthorize("@auth.puedeAccederLluerna(#usuario)")
    public void quitar(@PathVariable Integer asociadoId, @AuthenticationPrincipal Usuario usuario) {
        lluernaService.quitarMiembro(asociadoId);
    }
}

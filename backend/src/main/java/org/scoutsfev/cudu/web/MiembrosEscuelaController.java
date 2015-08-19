package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.TipoMiembroEscuela;
import org.scoutsfev.cudu.domain.dto.MiembroEscuelaDto;
import org.scoutsfev.cudu.domain.dto.MiembroEscuelaEdicionDto;
import org.scoutsfev.cudu.services.LluernaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<MiembroEscuelaDto> listado() {
        return lluernaService.obtenerMiembros();
    }

    @RequestMapping(value = "/{asociadoId}", method = RequestMethod.PUT)
    public void añadir(@PathVariable Integer asociadoId, @RequestBody MiembroEscuelaEdicionDto edicionMiembroDto) {
        lluernaService.añadirMiembro(asociadoId, edicionMiembroDto.getCargo(), edicionMiembroDto.isMesaPedagogica());
    }

    @RequestMapping(value = "/{asociadoId}", method = RequestMethod.DELETE)
    public void quitar(@PathVariable Integer asociadoId) {
        lluernaService.quitarMiembro(asociadoId);
    }
}

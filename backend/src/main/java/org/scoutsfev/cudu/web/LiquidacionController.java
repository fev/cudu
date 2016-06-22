package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.dto.EditarLiquidacionDto;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;
import org.scoutsfev.cudu.storage.LiquidacionesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/liquidaciones")
public class LiquidacionController {

    private final LiquidacionesStorage storage;

    @Autowired
    public LiquidacionController(LiquidacionesStorage storage) {
        this.storage = storage;
    }

    @RequestMapping(value = "/grupos/{rondaId}", method = RequestMethod.GET)
    public List<LiquidacionGrupos> resumenPorGrupos(@PathVariable("rondaId") Short rondaId) {
        return storage.resumenPorGrupos(rondaId);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}", method = RequestMethod.GET)
    public LiquidacionBalanceDto balanceGrupo(@PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId) {
        return storage.balanceGrupo(grupoId, rondaId);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}", method = RequestMethod.POST)
    public LiquidacionBalanceDto crear(@PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId, @AuthenticationPrincipal Usuario usuario) {
        storage.crear(grupoId, rondaId, usuario.getUsername());
        return storage.balanceGrupo(grupoId, rondaId);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}/{liquidacionId}", method = RequestMethod.DELETE)
    public LiquidacionBalanceDto eliminar(
            @PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId, @PathVariable("liquidacionId") int liquidacionId,
            @AuthenticationPrincipal Usuario usuario) {
        storage.eliminar(liquidacionId);
        return storage.balanceGrupo(grupoId, rondaId);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}/{liquidacionId}", method = RequestMethod.PUT)
    public LiquidacionBalanceDto editar(
            @PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId, @PathVariable("liquidacionId") int liquidacionId,
            @RequestBody @Valid EditarLiquidacionDto editada, @AuthenticationPrincipal Usuario usuario) {
        storage.editar(liquidacionId, editada.ajusteManual, editada.pagado, editada.borrador);
        return storage.balanceGrupo(grupoId, rondaId);
    }
}

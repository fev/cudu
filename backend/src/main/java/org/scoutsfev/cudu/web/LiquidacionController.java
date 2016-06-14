package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;
import org.scoutsfev.cudu.storage.LiquidacionesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        List<LiquidacionBalance> balances = storage.balanceGrupo(grupoId, rondaId);
        LiquidacionGrupos resumen = storage.resumenPorGrupo(grupoId, rondaId);
        return new LiquidacionBalanceDto(resumen.getActivos(), resumen.getBalance(), balances);
    }
}

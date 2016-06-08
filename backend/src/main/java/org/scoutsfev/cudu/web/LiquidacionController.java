package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
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

    @RequestMapping(value = "/grupos", method = RequestMethod.GET)
    public List<LiquidacionGrupos> resumenPorGrupos() {
        return storage.resumenPorGrupos();
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}", method = RequestMethod.GET)
    public List<LiquidacionBalance> balanceGrupo(@PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId) {
        return storage.balanceGrupo(grupoId, rondaId);
    }
}

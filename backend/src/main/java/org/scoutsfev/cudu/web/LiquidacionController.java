package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.domain.dto.EditarLiquidacionDto;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;
import org.scoutsfev.cudu.domain.dto.LiquidacionDesgloseDto;
import org.scoutsfev.cudu.storage.Borradores;
import org.scoutsfev.cudu.storage.LiquidacionesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping(value = "/{liquidacionId}", method = RequestMethod.GET)
    public LiquidacionDesgloseDto desglose(@PathVariable("liquidacionId") int liquidacionId) {
        return storage.desglose(liquidacionId);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}", method = RequestMethod.GET)
    public LiquidacionBalanceDto balanceGrupo(@PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo,
            @AuthenticationPrincipal Usuario usuario) {
        // TODO Seguridad: Si el usuario no es técnico, obtener balances del grupo del usuario
        // TODO Seguridad: El tecnico SdA solo puede ver grupos de su asociacion
        // TODO Seguridad: El usuario tiene permisos para ver/editar los datos del grupo
        return this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}", method = RequestMethod.POST)
    public LiquidacionBalanceDto crear(@PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo,
            @AuthenticationPrincipal Usuario usuario) {
        storage.crear(grupoId, rondaId, usuario.getUsername());
        return this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}/{liquidacionId}", method = RequestMethod.DELETE)
    public LiquidacionBalanceDto eliminar(
            @PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId, @PathVariable("liquidacionId") int liquidacionId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo,
            @AuthenticationPrincipal Usuario usuario) {
        storage.eliminar(liquidacionId);
        return this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario);
    }

    @RequestMapping(value = "/balance/{grupoId}/{rondaId}/{liquidacionId}", method = RequestMethod.PUT)
    public LiquidacionBalanceDto editar(
            @PathVariable("grupoId") String grupoId, @PathVariable("rondaId") Short rondaId, @PathVariable("liquidacionId") int liquidacionId,
            @RequestParam(value = "incluirBorradoresEnCalculo", required = false) Boolean incluirBorradoresEnCalculo,
            @RequestBody @Valid EditarLiquidacionDto editada, @AuthenticationPrincipal Usuario usuario) {
        storage.editar(liquidacionId, editada.ajusteManual, editada.pagado, editada.borrador);
        return this.balanceDeGrupo(grupoId, rondaId, incluirBorradoresEnCalculo, usuario);
    }

    private LiquidacionBalanceDto balanceDeGrupo(String grupoId, Short rondaId, Boolean incluirBorradoresEnCalculo, Usuario usuario) {
        boolean borradores = incluirBorradoresEnCalculo == null ? false : incluirBorradoresEnCalculo;
        // TODO Usuario es tecnico
        boolean usuarioEsTecnico = true;
        if (!usuarioEsTecnico)
            return storage.balanceGrupo(grupoId, rondaId, Borradores.Ocultar);
        else
            return storage.balanceGrupo(grupoId, rondaId, borradores ? Borradores.MostrarCalculando : Borradores.MostrarSinCalcular);
    }
}

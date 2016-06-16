package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.db.tables.pojos.InformacionPago;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;

import java.util.List;

public interface LiquidacionesStorage {
    LiquidacionGrupos resumenPorGrupo(String grupoId, short rondaId);
    List<LiquidacionGrupos> resumenPorGrupos(short rondaId);
    List<LiquidacionBalance> balanceGrupo(String grupoId, short rondaId);
    InformacionPago informacionPago(String grupoId);
}

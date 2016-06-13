package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;

import java.util.List;

public interface LiquidacionesStorage {
    LiquidacionGrupos resumenPorGrupo(String grupoId, Short rondaId);
    List<LiquidacionGrupos> resumenPorGrupos();
    List<LiquidacionBalance> balanceGrupo(String grupoId, Short rondaId);
}

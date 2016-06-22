package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;

import java.math.BigDecimal;
import java.util.List;

public interface LiquidacionesStorage {
    List<LiquidacionGrupos> resumenPorGrupos(short rondaId);
    LiquidacionBalanceDto balanceGrupo(String grupoId, short rondaId);
    int crear(String grupoId, short rondaId, String creadoPor);
    void eliminar(int liquidacionId);
    void editar(int liquidacionId, BigDecimal ajusteManual, BigDecimal pagado, boolean borrador);
}

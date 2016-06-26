package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;
import org.scoutsfev.cudu.domain.dto.LiquidacionDesgloseDto;

import java.math.BigDecimal;
import java.util.List;

public interface LiquidacionesStorage {
    List<LiquidacionGrupos> resumenPorGrupos(short rondaId);
    LiquidacionDesgloseDto desglose(int liquidacionId);
    LiquidacionBalanceDto balanceGrupo(String grupoId, short rondaId, Borradores borradores);
    int crear(String grupoId, short rondaId, String creadoPor);
    void eliminar(int liquidacionId);
    void editar(int liquidacionId, BigDecimal ajusteManual, BigDecimal pagado, boolean borrador);
}

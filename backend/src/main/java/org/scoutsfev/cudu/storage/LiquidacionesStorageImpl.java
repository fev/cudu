package org.scoutsfev.cudu.storage;

import org.jooq.DSLContext;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.scoutsfev.cudu.db.Tables.LIQUIDACION_BALANCE;
import static org.scoutsfev.cudu.db.Tables.LIQUIDACION_GRUPOS;

@Repository
public class LiquidacionesStorageImpl implements LiquidacionesStorage {

    private final DSLContext context;

    @Autowired
    public LiquidacionesStorageImpl(DSLContext context) {
        this.context = context;
    }

    public List<LiquidacionGrupos> resumenPorGrupos() {
        return context.selectFrom(LIQUIDACION_GRUPOS).fetchInto(LiquidacionGrupos.class);
    }

    @Override
    public List<LiquidacionBalance> balanceGrupo(String grupoId, Short rondaId) {
        return context.selectFrom(LIQUIDACION_BALANCE)
                .where(LIQUIDACION_BALANCE.GRUPO_ID.equal(grupoId))
                .and(LIQUIDACION_BALANCE.RONDA_ID.equal(rondaId))
                .fetchInto(LiquidacionBalance.class);
    }
}

package org.scoutsfev.cudu.storage;

import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.scoutsfev.cudu.db.tables.pojos.InformacionPago;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalanceResumen;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionGrupos;
import org.scoutsfev.cudu.db.tables.records.LiquidacionBalanceRecord;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static org.scoutsfev.cudu.db.Routines.crearLiquidacion;
import static org.scoutsfev.cudu.db.Tables.*;

@Repository
public class LiquidacionesStorageImpl implements LiquidacionesStorage {

    private final DSLContext context;

    @Autowired
    public LiquidacionesStorageImpl(DSLContext context) {
        this.context = context;
    }

    public List<LiquidacionGrupos> resumenPorGrupos(short rondaId) {
        return context.selectFrom(LIQUIDACION_GRUPOS)
                .where(LIQUIDACION_GRUPOS.RONDA_ID.equal(rondaId))
                .orderBy(LIQUIDACION_GRUPOS.NOMBRE)
                .fetchInto(LiquidacionGrupos.class);
    }

    @Override
    public LiquidacionBalanceDto balanceGrupo(String grupoId, short rondaId, Borradores borradores) {
        SelectConditionStep<LiquidacionBalanceRecord> query = context
                .selectFrom(LIQUIDACION_BALANCE)
                .where(LIQUIDACION_BALANCE.GRUPO_ID.equal(grupoId))
                .and(LIQUIDACION_BALANCE.RONDA_ID.equal(rondaId));

        if (borradores == Borradores.Ocultar) {
            query = query.and(LIQUIDACION_BALANCE.BORRADOR.equal(false));
        }

        List<LiquidacionBalance> balances =  query.fetchInto(LiquidacionBalance.class);
        LiquidacionBalanceResumen resumen = resumenPorGrupo(grupoId, rondaId);
        InformacionPago informacionPago = informacionPago(grupoId);

        BigDecimal totalBalance;
        if (borradores == Borradores.MostrarCalculando)
            totalBalance = resumen.getBalanceConBorradores();
        else
            totalBalance = resumen.getBalanceSinBorradores();

        return new LiquidacionBalanceDto(resumen.getGrupoNombre(), resumen.getActivos(), totalBalance, balances, informacionPago);
    }

    public int crear(String grupoId, short rondaId, String creadoPor) {
        return crearLiquidacion(context.configuration(), grupoId, rondaId, creadoPor);
    }

    public void eliminar(int liquidacionId) {
        context.transaction(configuration -> {
            DSL.using(configuration)
                .deleteFrom(LIQUIDACION_ASOCIADO)
                .where(LIQUIDACION_ASOCIADO.LIQUIDACION_ID.equal(liquidacionId))
                .execute();

            DSL.using(configuration)
                .deleteFrom(LIQUIDACION)
                .where(LIQUIDACION.ID.equal(liquidacionId))
                .execute();
        });
    }

    public void editar(int liquidacionId, BigDecimal ajusteManual, BigDecimal pagado, boolean borrador) {
        context.update(LIQUIDACION)
                .set(LIQUIDACION.AJUSTE_MANUAL, ajusteManual)
                .set(LIQUIDACION.PAGADO, pagado)
                .set(LIQUIDACION.BORRADOR, borrador)
                .where(LIQUIDACION.ID.equal(liquidacionId))
                .execute();
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    private InformacionPago informacionPago(String grupoId) {
        return context.select(INFORMACION_PAGO.fields()).from(INFORMACION_PAGO)
                .innerJoin(GRUPO).on(GRUPO.ASOCIACION.equal(INFORMACION_PAGO.ASOCIACIONID))
                .where(GRUPO.ID.equal(grupoId))
                .fetchAnyInto(InformacionPago.class);
    }

    private LiquidacionBalanceResumen resumenPorGrupo(String grupoId, short rondaId) {
        return context.selectFrom(LIQUIDACION_BALANCE_RESUMEN)
                .where(LIQUIDACION_BALANCE_RESUMEN.GRUPO_ID.equal(grupoId))
                .and(LIQUIDACION_BALANCE_RESUMEN.RONDA_ID.equal(rondaId))
                .fetchAnyInto(LiquidacionBalanceResumen.class);
    }
}

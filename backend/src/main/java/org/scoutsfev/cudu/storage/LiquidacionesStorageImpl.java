package org.scoutsfev.cudu.storage;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.scoutsfev.cudu.db.tables.pojos.*;
import org.scoutsfev.cudu.db.tables.records.LiquidacionBalanceRecord;
import org.scoutsfev.cudu.domain.dto.LiquidacionBalanceDto;
import org.scoutsfev.cudu.domain.dto.LiquidacionDesgloseDto;
import org.scoutsfev.cudu.domain.dto.ValoresEstadisticos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.scoutsfev.cudu.db.Routines.crearLiquidacion;
import static org.scoutsfev.cudu.db.Tables.*;

@Repository
public class LiquidacionesStorageImpl implements LiquidacionesStorage {

    private final DSLContext context;

    private static final Field<Long> especificidadField = field(name("especificidad"), Long.class);
    private static final Collection<? extends SelectField<?>> camposValoresEstadisticos = Arrays.asList(
            field(name("ambito")), especificidadField,
            field(name("joven")), field(name("kraal")), field(name("comite")),
            field(name("colonia")), field(name("manada")), field(name("exploradores")), field(name("expedicion")), field(name("ruta")),
            field(name("total")));

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

    public LiquidacionDesgloseDto desglose(int liquidacionId) {
        Record2<String, Short> meta = context.select(LIQUIDACION.GRUPO_ID, LIQUIDACION.RONDA_ID)
                .from(LIQUIDACION)
                .where(LIQUIDACION.ID.equal(liquidacionId))
                .fetchOne();

        List<LiquidacionAsociado> asociados = context.selectFrom(LIQUIDACION_ASOCIADO)
                .where(LIQUIDACION_ASOCIADO.LIQUIDACION_ID.equal(liquidacionId))
                .fetchInto(LiquidacionAsociado.class);

        String grupoId = meta.getValue(LIQUIDACION.GRUPO_ID);

        List<ValoresEstadisticos> valoresEstadisticos = obtenerValoresEstadisticos(liquidacionId, grupoId);

        String referencia = String.format("%s-%d-%d", grupoId, meta.getValue(LIQUIDACION.RONDA_ID), liquidacionId);
        return new LiquidacionDesgloseDto(referencia, asociados, valoresEstadisticos);
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

        BigDecimal precioPorAsociado = informacionPago.getPrecioporasociado();
        if (Objects.equals(precioPorAsociado, BigDecimal.ZERO))
            precioPorAsociado = BigDecimal.ONE;
        BigDecimal acumuladoAsociados = totalBalance.divide(precioPorAsociado, 0, BigDecimal.ROUND_FLOOR);

        return new LiquidacionBalanceDto(resumen.getGrupoNombre(), resumen.getActivos(), totalBalance, acumuladoAsociados, balances, informacionPago);
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

    private List<ValoresEstadisticos> obtenerValoresEstadisticos(int liquidacionId, String grupoId) {
        return context
                .select(camposValoresEstadisticos)
                .from(VALORES_POR_LIQUIDACION)
                .where(VALORES_POR_LIQUIDACION.LIQUIDACION_ID.equal(liquidacionId))
                .union(context
                        .select(camposValoresEstadisticos)
                        .from(VALORES_POR_ASOCIACION)
                        .innerJoin(GRUPO).on(GRUPO.ASOCIACION.equal(VALORES_POR_ASOCIACION.ASOCIACIONID))
                        .where(GRUPO.ID.equal(grupoId))
                ).union(context
                        .select(camposValoresEstadisticos)
                        .from(VALORES_FEDERATIVOS)
                )
                .orderBy(especificidadField.desc())
                .fetchInto(ValoresEstadisticos.class);
    }
}

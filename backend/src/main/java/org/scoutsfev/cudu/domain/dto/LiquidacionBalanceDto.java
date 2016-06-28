package org.scoutsfev.cudu.domain.dto;

import org.scoutsfev.cudu.db.tables.pojos.InformacionPago;
import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class LiquidacionBalanceDto {
    public final String nombreGrupo;
    public final long numeroActualAsociados;
    public final BigDecimal total;
    public final BigDecimal acumuladoAsociados;
    public final List<LiquidacionBalance> balance;
    public final InformacionPago informacionPago;

    public LiquidacionBalanceDto(String nombreGrupo, Long numeroActualAsociados, BigDecimal total, BigDecimal acumuladoAsociados, List<LiquidacionBalance> balance, InformacionPago informacionPago) {
        this.nombreGrupo = nombreGrupo;
        this.numeroActualAsociados = numeroActualAsociados;
        this.total = total;
        this.acumuladoAsociados = acumuladoAsociados;
        this.balance = balance;
        this.informacionPago = informacionPago;
    }
}

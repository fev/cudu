package org.scoutsfev.cudu.domain.dto;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionBalance;

import java.math.BigDecimal;
import java.util.List;

public class LiquidacionBalanceDto {
    public final long numeroActualAsociados;
    public final BigDecimal total;
    public final List<LiquidacionBalance> balance;

    public LiquidacionBalanceDto(Long numeroActualAsociados, BigDecimal total, List<LiquidacionBalance> balance) {
        this.numeroActualAsociados = numeroActualAsociados;
        this.total = total;
        this.balance = balance;
    }
}

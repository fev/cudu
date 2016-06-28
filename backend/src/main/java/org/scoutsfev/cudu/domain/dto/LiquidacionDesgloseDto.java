package org.scoutsfev.cudu.domain.dto;

import org.scoutsfev.cudu.db.tables.pojos.LiquidacionAsociado;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class LiquidacionDesgloseDto {
    public String referencia;
    public List<LiquidacionAsociado> asociados;
    public List<ValoresEstadisticos> estadisticas;

    public LiquidacionDesgloseDto(String referencia, List<LiquidacionAsociado> asociados, List<ValoresEstadisticos> estadisticas) {
        this.referencia = referencia;
        this.asociados = asociados;
        this.estadisticas = estadisticas;
    }
}

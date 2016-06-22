package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class EditarLiquidacionDto {

    @NotNull
    public final int id;

    @Min(0)
    public final BigDecimal ajusteManual;

    @Min(0)
    public final BigDecimal pagado;

    @NotNull
    public final boolean borrador;

    @JsonCreator
    public EditarLiquidacionDto(
            @JsonProperty("id") int id,
            @JsonProperty("ajusteManual") BigDecimal ajusteManual,
            @JsonProperty("pagado") BigDecimal pagado,
            @JsonProperty("borrador") boolean borrador) {
        this.id = id;
        this.ajusteManual = ajusteManual;
        this.pagado = pagado;
        this.borrador = borrador;
    }
}

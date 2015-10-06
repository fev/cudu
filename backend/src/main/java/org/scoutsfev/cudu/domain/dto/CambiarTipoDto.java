package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.scoutsfev.cudu.domain.TipoAsociado;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class CambiarTipoDto {
    @NotNull public final ArrayList<Integer> asociados;
    @NotNull public final TipoAsociado tipo;

    @JsonCreator
    public CambiarTipoDto(@JsonProperty("asociados") ArrayList<Integer> asociados, @JsonProperty("tipo") TipoAsociado tipo) {
        this.asociados = asociados;
        this.tipo = tipo;
    }
}

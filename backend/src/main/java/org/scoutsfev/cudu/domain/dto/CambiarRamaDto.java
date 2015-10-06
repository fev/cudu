package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.scoutsfev.cudu.domain.Rama;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class CambiarRamaDto {

    @NotNull public final ArrayList<Integer> asociados;
    @NotNull public final Rama rama;

    @JsonCreator
    public CambiarRamaDto(@JsonProperty("asociados") ArrayList<Integer> asociados, @JsonProperty("rama") Rama rama) {
        this.asociados = asociados;
        this.rama = rama;
    }
}

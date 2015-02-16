package org.scoutsfev.cudu.domain.dto;

import java.io.Serializable;

public class ActividadDetalleDtoPk implements Serializable {

    Integer actividadId;
    Integer asociadoId;

    public ActividadDetalleDtoPk() { }

    public ActividadDetalleDtoPk(Integer actividadId, Integer asociadoId) {
        this.actividadId = actividadId;
        this.asociadoId = asociadoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActividadDetalleDtoPk)) return false;
        ActividadDetalleDtoPk that = (ActividadDetalleDtoPk) o;
        return !(actividadId != null ? !actividadId.equals(that.actividadId) : that.actividadId != null)
                && !(asociadoId != null ? !asociadoId.equals(that.asociadoId) : that.asociadoId != null);
    }

    @Override
    public int hashCode() {
        int result = actividadId != null ? actividadId.hashCode() : 0;
        result = 31 * result + (asociadoId != null ? asociadoId.hashCode() : 0);
        return result;
    }
}

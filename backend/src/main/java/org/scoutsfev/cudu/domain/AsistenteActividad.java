package org.scoutsfev.cudu.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class AsistenteActividad implements Serializable {

    @Id
    @ManyToOne
    protected Actividad actividad;

    @Id
    @ManyToOne
    protected Asociado asociado;

    @NotNull
    protected EstadoAsistente estado = EstadoAsistente.Duda;

    protected AsistenteActividad() { }

    public AsistenteActividad(Actividad actividad, Asociado asociado) {
        this.actividad = actividad;
        this.asociado = asociado;
        this.estado = EstadoAsistente.Duda;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public Asociado getAsociado() {
        return asociado;
    }

    public EstadoAsistente getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsistente estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsistenteActividad)) return false;
        AsistenteActividad that = (AsistenteActividad) o;
        return !(actividad != null ? !actividad.equals(that.actividad) : that.actividad != null)
            && !(asociado != null ? !asociado.equals(that.asociado) : that.asociado != null)
            && estado == that.estado;
    }

    @Override
    public int hashCode() {
        int result = actividad != null ? actividad.hashCode() : 0;
        result = 31 * result + (asociado != null ? asociado.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }
}

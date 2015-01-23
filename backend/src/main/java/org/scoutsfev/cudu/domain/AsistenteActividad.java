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
    protected EstadoActividad estado = EstadoActividad.Incognita;

    protected AsistenteActividad() { }

    public AsistenteActividad(Actividad actividad, Asociado asociado) {
        this.actividad = actividad;
        this.asociado = asociado;
        this.estado = EstadoActividad.Incognita;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public Asociado getAsociado() {
        return asociado;
    }

    public EstadoActividad getEstado() {
        return estado;
    }

    public void setEstado(EstadoActividad estado) {
        this.estado = estado;
    }
}

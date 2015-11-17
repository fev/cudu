package org.scoutsfev.cudu.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Restricciones {

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean noPuedeEditarDatosDelGrupo = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean noPuedeEditarOtrasRamas = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean soloLectura = false;

    @Column(nullable = true)
    private Asociacion restriccionAsociacion = null;

    public boolean isNoPuedeEditarDatosDelGrupo() {
        return noPuedeEditarDatosDelGrupo;
    }

    public void setNoPuedeEditarDatosDelGrupo(boolean noPuedeEditarDatosDelGrupo) {
        this.noPuedeEditarDatosDelGrupo = noPuedeEditarDatosDelGrupo;
    }

    public boolean isNoPuedeEditarOtrasRamas() {
        return noPuedeEditarOtrasRamas;
    }

    public void setNoPuedeEditarOtrasRamas(boolean noPuedeEditarOtrasRamas) {
        this.noPuedeEditarOtrasRamas = noPuedeEditarOtrasRamas;
    }

    public boolean isSoloLectura() {
        return soloLectura;
    }

    public void setSoloLectura(boolean soloLectura) {
        this.soloLectura = soloLectura;
    }

    public Asociacion getRestriccionAsociacion() {
        return restriccionAsociacion;
    }

    public void setRestriccionAsociacion(Asociacion restriccionAsociacion) {
        this.restriccionAsociacion = restriccionAsociacion;
    }

    @Transient
    public boolean tieneAlgunaRestriccion() {
        return noPuedeEditarDatosDelGrupo || noPuedeEditarOtrasRamas || soloLectura || restriccionAsociacion != null;
    }
}

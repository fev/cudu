package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Rama {

    @Column(name = "rama_colonia", nullable = false, columnDefinition = "boolean default false")
    private boolean colonia = false;

    @Column(name = "rama_manada", nullable = false, columnDefinition = "boolean default false")
    private boolean manada = false;

    @Column(name = "rama_exploradores", nullable = false, columnDefinition = "boolean default false")
    private boolean exploradores = false;

    @Column(name = "rama_expedicion", nullable = false, columnDefinition = "boolean default false")
    private boolean expedicion = false;

    @Column(name = "rama_ruta", nullable = false, columnDefinition = "boolean default false")
    private boolean ruta = false;

    @Transient
    @JsonProperty("alguna")
    public boolean algunaSeleccionada() {
        return colonia || manada || exploradores || expedicion || ruta;
    }

    public boolean isColonia() {
        return colonia;
    }

    public void setColonia(boolean colonia) {
        this.colonia = colonia;
    }

    public boolean isManada() {
        return manada;
    }

    public void setManada(boolean manada) {
        this.manada = manada;
    }

    public boolean isExploradores() {
        return exploradores;
    }

    public void setExploradores(boolean exploradores) {
        this.exploradores = exploradores;
    }

    public boolean isExpedicion() {
        return expedicion;
    }

    public void setExpedicion(boolean expedicion) {
        this.expedicion = expedicion;
    }

    public boolean isRuta() {
        return ruta;
    }

    public void setRuta(boolean ruta) {
        this.ruta = ruta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rama)) return false;

        Rama rama = (Rama) o;
        return colonia == rama.colonia
            && expedicion == rama.expedicion
            && exploradores == rama.exploradores
            && manada == rama.manada
            && ruta == rama.ruta;
    }

    @Override
    public int hashCode() {
        int result = (colonia ? 1 : 0);
        result = 31 * result + (manada ? 1 : 0);
        result = 31 * result + (exploradores ? 1 : 0);
        result = 31 * result + (expedicion ? 1 : 0);
        result = 31 * result + (ruta ? 1 : 0);
        return result;
    }
}

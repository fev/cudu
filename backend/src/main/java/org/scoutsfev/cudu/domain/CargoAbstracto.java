package org.scoutsfev.cudu.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class CargoAbstracto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotNull
    @Size(max = 50)
    protected String etiqueta;

    @NotNull
    protected AmbitoCargo ambito = AmbitoCargo.Kraal;

    @NotNull
    @Column(nullable = false, columnDefinition = "boolean default false")
    protected boolean unico = false;

    @NotNull
    @Min(0)
    protected int puntos = 0;

    protected CargoAbstracto() { }

    public CargoAbstracto(AmbitoCargo ambito, String etiqueta, int puntos) {
        this.ambito = ambito;
        this.etiqueta = etiqueta;
        this.puntos = puntos;
        this.unico = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public AmbitoCargo getAmbito() {
        return ambito;
    }

    public void setAmbito(AmbitoCargo ambito) {
        this.ambito = ambito;
    }

    public boolean isUnico() {
        return unico;
    }

    public void setUnico(boolean unico) {
        this.unico = unico;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}

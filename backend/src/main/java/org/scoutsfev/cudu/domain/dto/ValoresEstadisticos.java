package org.scoutsfev.cudu.domain.dto;

import java.io.Serializable;

public class ValoresEstadisticos implements Serializable {

    private static final long serialVersionUID = -232142352;

    private final String ambito;
    private final Long especificidad;
    private final Long joven;
    private final Long kraal;
    private final Long comite;
    private final Long colonia;
    private final Long manada;
    private final Long exploradores;
    private final Long expedicion;
    private final Long ruta;
    private final Long total;

    public ValoresEstadisticos(ValoresEstadisticos value) {
        this.ambito = value.ambito;
        this.especificidad = value.especificidad;
        this.joven = value.joven;
        this.kraal = value.kraal;
        this.comite = value.comite;
        this.colonia = value.colonia;
        this.manada = value.manada;
        this.exploradores = value.exploradores;
        this.expedicion = value.expedicion;
        this.ruta = value.ruta;
        this.total = value.total;
    }

    public ValoresEstadisticos(
            String ambito,
            Long especificidad,
            Long joven,
            Long kraal,
            Long comite,
            Long colonia,
            Long manada,
            Long exploradores,
            Long expedicion,
            Long ruta,
            Long total
    ) {
        this.ambito = ambito;
        this.especificidad = especificidad;
        this.joven = joven;
        this.kraal = kraal;
        this.comite = comite;
        this.colonia = colonia;
        this.manada = manada;
        this.exploradores = exploradores;
        this.expedicion = expedicion;
        this.ruta = ruta;
        this.total = total;
    }

    public String getAmbito() {
        return ambito;
    }

    public Long getEspecificidad() {
        return especificidad;
    }

    public Long getJoven() {
        return this.joven;
    }

    public Long getKraal() {
        return this.kraal;
    }

    public Long getComite() {
        return this.comite;
    }

    public Long getColonia() {
        return this.colonia;
    }

    public Long getManada() {
        return this.manada;
    }

    public Long getExploradores() {
        return this.exploradores;
    }

    public Long getExpedicion() {
        return this.expedicion;
    }

    public Long getRuta() {
        return this.ruta;
    }

    public Long getTotal() {
        return this.total;
    }
}

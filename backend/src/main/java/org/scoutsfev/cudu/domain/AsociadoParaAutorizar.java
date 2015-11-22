package org.scoutsfev.cudu.domain;

import java.io.Serializable;

public class AsociadoParaAutorizar implements Serializable {

    private static final long serialVersionUID = 769833832;

    public final int id;

    public final String grupoId;

    public final Integer asociacionId;

    public final boolean ramaColonia;

    public final boolean ramaManada;

    public final boolean ramaExploradores;

    public final boolean ramaExpedicion;

    public final boolean ramaRuta;

    public AsociadoParaAutorizar(AsociadoParaAutorizar value) {
        this.id = value.id;
        this.grupoId = value.grupoId;
        this.asociacionId = value.asociacionId;
        this.ramaColonia = value.ramaColonia;
        this.ramaManada = value.ramaManada;
        this.ramaExploradores = value.ramaExploradores;
        this.ramaExpedicion = value.ramaExpedicion;
        this.ramaRuta = value.ramaRuta;
    }

    public AsociadoParaAutorizar(int id, String grupoId, Integer asociacionId, boolean ramaColonia, boolean ramaManada, boolean ramaExploradores, boolean ramaExpedicion, boolean ramaRuta) {
        this.id = id;
        this.grupoId = grupoId;
        this.asociacionId = asociacionId;
        this.ramaColonia = ramaColonia;
        this.ramaManada = ramaManada;
        this.ramaExploradores = ramaExploradores;
        this.ramaExpedicion = ramaExpedicion;
        this.ramaRuta = ramaRuta;
    }

    public int getId() {
        return id;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public Integer getAsociacionId() {
        return asociacionId;
    }

    public boolean isRamaColonia() {
        return ramaColonia;
    }

    public boolean isRamaManada() {
        return ramaManada;
    }

    public boolean isRamaExploradores() {
        return ramaExploradores;
    }

    public boolean isRamaExpedicion() {
        return ramaExpedicion;
    }

    public boolean isRamaRuta() {
        return ramaRuta;
    }
}

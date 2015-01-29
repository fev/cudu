package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dto_datos_rama_tipo")
@Immutable
public class DatosPorRamaTipoDto {

    @Id
    @Column(name = "es_joven")
    @JsonIgnore
    private boolean esJoven;

    private long colonia;
    private long manada;
    private long exploradores;
    private long expedicion;
    private long ruta;
    private long total;

    public boolean isEsJoven() {
        return esJoven;
    }

    public long getColonia() {
        return colonia;
    }

    public long getManada() {
        return manada;
    }

    public long getExploradores() {
        return exploradores;
    }

    public long getExpedicion() {
        return expedicion;
    }

    public long getRuta() {
        return ruta;
    }

    public long getTotal() {
        return total;
    }
}

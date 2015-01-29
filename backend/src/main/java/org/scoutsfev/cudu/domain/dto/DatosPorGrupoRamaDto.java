package org.scoutsfev.cudu.domain.dto;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dto_datos_grupo_rama")
@Immutable
public class DatosPorGrupoRamaDto {

    @Id
    @Column(name = "grupo_id")
    private String id;
    private long colonia;
    private long manada;
    private long exploradores;
    private long expedicion;
    private long ruta;

    public String getId() {
        return id;
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
}

package org.scoutsfev.cudu.domain.dto;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dto_datos_grupo_tipo")
@Immutable
public class DatosPorGrupoTipoDto {

    @Id
    @Column(name = "grupo_id")
    private String id;
    private long joven;
    private long kraal;
    private long comite;

    public String getId() {
        return id;
    }

    public long getJoven() {
        return joven;
    }

    public long getKraal() {
        return kraal;
    }

    public long getComite() {
        return comite;
    }
}

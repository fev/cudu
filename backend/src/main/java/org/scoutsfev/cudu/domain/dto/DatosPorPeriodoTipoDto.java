package org.scoutsfev.cudu.domain.dto;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dto_datos_periodo_tipo")
@Immutable
public class DatosPorPeriodoTipoDto {

    @Id
    private int periodo;
    private long jovenes;
    private long voluntarios;
    private long total;

    public int getPeriodo() {
        return periodo;
    }

    public long getJovenes() {
        return jovenes;
    }

    public long getVoluntarios() {
        return voluntarios;
    }

    public long getTotal() {
        return total;
    }
}

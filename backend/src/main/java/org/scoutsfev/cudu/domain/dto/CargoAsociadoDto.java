package org.scoutsfev.cudu.domain.dto;

import org.hibernate.annotations.Immutable;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.CargoAbstracto;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dto_cargo_asociado")
@Immutable
public class CargoAsociadoDto extends CargoAbstracto {

    @ManyToOne
    @JoinColumn(name = "asociado_id", referencedColumnName = "id")
    protected Asociado asociado;
}

package org.scoutsfev.cudu.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.hibernate.annotations.Immutable;
import org.scoutsfev.cudu.domain.EstadoAsistente;
import org.scoutsfev.cudu.domain.Rama;
import org.scoutsfev.cudu.domain.TipoAsociado;

import javax.persistence.*;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Entity
@Table(name = "dto_actividad_detalle")
@IdClass(ActividadDetalleDtoPk.class)
@Immutable
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ActividadDetalleDto implements Serializable {

    @Id
    Integer actividadId;

    @Id
    Integer asociadoId;

    String grupoId;
    EstadoAsistente estadoAsistente;
    String nombreCompleto;
    TipoAsociado tipo;

    @Embedded
    Rama rama;

    String telefono;
}


package org.scoutsfev.cudu.domain;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.scoutsfev.cudu.RegistroImpresionId;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@IdClass(RegistroImpresionId.class)
@Table(name = "impresion")
public class RegistroImpresion {

    public RegistroImpresion() {
    }

    public RegistroImpresion(Integer usuarioId, String fichero) {
        this.usuarioId = usuarioId;
        this.fichero = fichero;
    }

    @Id
    private Integer usuarioId;

    @Id
    @Column(length = 100)
    private String fichero;

    @Column(name="fecha", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fecha;

    public Integer getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFichero() {
        return this.fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public Timestamp getFecha() {
        return this.fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}

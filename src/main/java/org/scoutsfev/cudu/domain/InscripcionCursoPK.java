
package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class InscripcionCursoPK implements Serializable {
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcurso")
    private int idcurso;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "idmonografico")
    private int idmonografico;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "idasociado")
    private int idasociado;

    /**
     * @return the idcurso
     */
    public int getIdcurso() {
        return idcurso;
    }

    /**
     * @param idcurso the idcurso to set
     */
    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    /**
     * @return the idmonografico
     */
    public int getIdmonografico() {
        return idmonografico;
    }

    /**
     * @param idmonografico the idmonografico to set
     */
    public void setIdmonografico(int idmonografico) {
        this.idmonografico = idmonografico;
    }

    /**
     * @return the idasociado
     */
    public int getIdasociado() {
        return idasociado;
    }

    /**
     * @param idasociado the idasociado to set
     */
    public void setIdasociado(int idasociado) {
        this.idasociado = idasociado;
    }
}

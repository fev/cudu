/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author gaxp
 */
@Embeddable
public class MonograficosEnCursosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcurso")
    private int idcurso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idmonografico")
    private int idmonografico;

    public MonograficosEnCursosPK() {
    }

    public MonograficosEnCursosPK(int idcurso, int idmonografico) {
        this.idcurso = idcurso;
        this.idmonografico = idmonografico;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    public int getIdmonografico() {
        return idmonografico;
    }

    public void setIdmonografico(int idmonografico) {
        this.idmonografico = idmonografico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcurso;
        hash += (int) idmonografico;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonograficosEnCursosPK)) {
            return false;
        }
        MonograficosEnCursosPK other = (MonograficosEnCursosPK) object;
        if (this.idcurso != other.idcurso) {
            return false;
        }
        if (this.idmonografico != other.idmonografico) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.scoutsfev.cudu.domain.MonograficosEnCursosPK[ idcurso=" + idcurso + ", idmonografico=" + idmonografico + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author gaxp
 */
 @Embeddable
public class MisCursosPK implements Serializable {
    @Column(name = "idAsociado", nullable = false)
    private int idAsociado;

    @Column(name = "idMonografico", nullable = false)
    private int idMonografico;
    
    @Column(name = "idCurso", nullable = false)
    private int idCurso;

    /**
     * @return the idAsociado
     */
    public int getIdAsociado() {
        return idAsociado;
    }

    /**
     * @param idAsociado the idAsociado to set
     */
    public void setIdAsociado(int idAsociado) {
        this.idAsociado = idAsociado;
    }

    /**
     * @return the idMonografico
     */
    public int getIdMonografico() {
        return idMonografico;
    }

    /**
     * @param idMonografico the idMonografico to set
     */
    public void setIdMonografico(int idMonografico) {
        this.idMonografico = idMonografico;
    }

    /**
     * @return the idCurso
     */
    public int getIdCurso() {
        return idCurso;
    }

    /**
     * @param idCurso the idCurso to set
     */
    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
    
    
    
}

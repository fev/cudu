/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gaxp
 */
@Entity
@Table(name = "monograficos_en_cursos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonograficosEnCursos.findAll", query = "SELECT m FROM MonograficosEnCursos m"),
    @NamedQuery(name = "MonograficosEnCursos.findByIdcurso", query = "SELECT m FROM MonograficosEnCursos m WHERE m.monograficosEnCursosPK.idcurso = :idcurso"),
    @NamedQuery(name = "MonograficosEnCursos.findByIdmonografico", query = "SELECT m FROM MonograficosEnCursos m WHERE m.monograficosEnCursosPK.idmonografico = :idmonografico"),
    @NamedQuery(name = "MonograficosEnCursos.findByBloque", query = "SELECT m FROM MonograficosEnCursos m WHERE m.bloque = :bloque"),
    @NamedQuery(name = "MonograficosEnCursos.findByObligatorio", query = "SELECT m FROM MonograficosEnCursos m WHERE m.obligatorio = :obligatorio"),
    @NamedQuery(name = "MonograficosEnCursos.findByBloqueunico", query = "SELECT m FROM MonograficosEnCursos m WHERE m.bloqueunico = :bloqueunico")})
public class MonograficosEnCursos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MonograficosEnCursosPK monograficosEnCursosPK;
    @Size(max = 100)
    @Column(name = "bloque")
    private String bloque;
    @Column(name = "obligatorio")
    private Boolean obligatorio;
    @Column(name = "bloqueunico")
    private Boolean bloqueunico;
    @JoinColumn(name = "idmonografico", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Monografico monografico;
    @JoinColumn(name = "idcurso", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curso curso;

    public MonograficosEnCursos() {
    }

    public MonograficosEnCursos(MonograficosEnCursosPK monograficosEnCursosPK) {
        this.monograficosEnCursosPK = monograficosEnCursosPK;
    }

    public MonograficosEnCursos(int idcurso, int idmonografico) {
        this.monograficosEnCursosPK = new MonograficosEnCursosPK(idcurso, idmonografico);
    }

    public MonograficosEnCursosPK getMonograficosEnCursosPK() {
        return monograficosEnCursosPK;
    }

    public void setMonograficosEnCursosPK(MonograficosEnCursosPK monograficosEnCursosPK) {
        this.monograficosEnCursosPK = monograficosEnCursosPK;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Boolean getBloqueunico() {
        return bloqueunico;
    }

    public void setBloqueunico(Boolean bloqueunico) {
        this.bloqueunico = bloqueunico;
    }

    public Monografico getMonografico() {
        return monografico;
    }

    public void setMonografico(Monografico monografico) {
        this.monografico = monografico;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (monograficosEnCursosPK != null ? monograficosEnCursosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonograficosEnCursos)) {
            return false;
        }
        MonograficosEnCursos other = (MonograficosEnCursos) object;
        if ((this.monograficosEnCursosPK == null && other.monograficosEnCursosPK != null) || (this.monograficosEnCursosPK != null && !this.monograficosEnCursosPK.equals(other.monograficosEnCursosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.scoutsfev.cudu.domain.MonograficosEnCursos[ monograficosEnCursosPK=" + monograficosEnCursosPK + " ]";
    }
    
}

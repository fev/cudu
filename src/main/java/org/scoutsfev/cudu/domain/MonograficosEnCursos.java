package org.scoutsfev.cudu.domain;

import javax.persistence.EmbeddedId;

import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
@Table(name = "monograficos_en_cursos")
public class MonograficosEnCursos implements Serializable {
   
    
    @EmbeddedId
    protected MonograficosEnCursosPK monograficosEnCursosPK;
    
    @Size(max = 100)
    @Column(name = "bloque")
    private String bloque;
    
    @Column(name = "obligatorio")
    private Boolean obligatorio;
    
    @Column(name = "bloqueunico")
    private Boolean bloqueunico;
    
    @Column(name = "fijo")
    private Boolean fijo;
    
    @JoinColumn(name = "idmonografico", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Monografico monografico;
    
    @JoinColumn(name = "idcurso", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curso curso;


    @Column(name = "bloque_numerominimo_monograficos")
    private int bloqueNumMinMonograficos;

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

    /**
     * @return the fijo
     */
    public Boolean getFijo() {
        return fijo;
    }

    /**
     * @param fijo the fijo to set
     */
    public void setFijo(Boolean fijo) {
        this.fijo = fijo;
    }
    
}

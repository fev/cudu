
package org.scoutsfev.cudu.domain;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Table(name = "inscripcioncurso")
public class InscripcionCurso  implements Serializable {

    @EmbeddedId
    private InscripcionCursoPK inscripcionCursoPK;
    
    @Temporal(TemporalType.DATE)
    private Date fechainscripcion;
    private boolean pagorealizado;
    private char trabajo;
    @Temporal(TemporalType.DATE)
    private Date fecha_entrega_trabajo;
    private char calificacion;
    private char idiomamateriales;
    private char modocontacto;
    private char formatomateriales;
    
      
    @JoinColumn(name = "idmonografico", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Monografico monografico;
    
    @JoinColumn(name = "idcurso", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curso curso;
    
    @JoinColumn(name = "idasociado", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Asociado asociado;

    /**
     * @return the inscripcionCursoPK
     */
    public InscripcionCursoPK getInscripcionCursoPK() {
        return inscripcionCursoPK;
    }

    /**
     * @param inscripcionCursoPK the inscripcionCursoPK to set
     */
    public void setInscripcionCursoPK(InscripcionCursoPK inscripcionCursoPK) {
        this.inscripcionCursoPK = inscripcionCursoPK;
    }

    /**
     * @return the fechainscripcion
     */
    public Date getFechainscripcion() {
        return fechainscripcion;
    }

    /**
     * @param fechainscripcion the fechainscripcion to set
     */
    public void setFechainscripcion(Date fechainscripcion) {
        this.fechainscripcion = fechainscripcion;
    }

    /**
     * @return the pagorealizado
     */
    public boolean isPagorealizado() {
        return pagorealizado;
    }

    /**
     * @param pagorealizado the pagorealizado to set
     */
    public void setPagorealizado(boolean pagorealizado) {
        this.pagorealizado = pagorealizado;
    }

    /**
     * @return the trabajo
     */
    public char getTrabajo() {
        return trabajo;
    }

    /**
     * @param trabajo the trabajo to set
     */
    public void setTrabajo(char trabajo) {
        this.trabajo = trabajo;
    }

    /**
     * @return the fecha_entrega_trabajo
     */
    public Date getFecha_entrega_trabajo() {
        return fecha_entrega_trabajo;
    }

    /**
     * @param fecha_entrega_trabajo the fecha_entrega_trabajo to set
     */
    public void setFecha_entrega_trabajo(Date fecha_entrega_trabajo) {
        this.fecha_entrega_trabajo = fecha_entrega_trabajo;
    }

    /**
     * @return the calificacion
     */
    public char getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(char calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the idiomamateriales
     */
    public char getIdiomamateriales() {
        return idiomamateriales;
    }

    /**
     * @param idiomamateriales the idiomamateriales to set
     */
    public void setIdiomamateriales(char idiomamateriales) {
        this.idiomamateriales = idiomamateriales;
    }

    /**
     * @return the modocontacto
     */
    public char getModocontacto() {
        return modocontacto;
    }

    /**
     * @param modocontacto the modocontacto to set
     */
    public void setModocontacto(char modocontacto) {
        this.modocontacto = modocontacto;
    }

    /**
     * @return the formatomateriales
     */
    public char getFormatomateriales() {
        return formatomateriales;
    }

    /**
     * @param formatomateriales the formatomateriales to set
     */
    public void setFormatomateriales(char formatomateriales) {
        this.formatomateriales = formatomateriales;
    }

    /**
     * @return the monografico
     */
    public Monografico getMonografico() {
        return monografico;
    }

    /**
     * @param monografico the monografico to set
     */
    public void setMonografico(Monografico monografico) {
        this.monografico = monografico;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the asociado
     */
    public Asociado getAsociado() {
        return asociado;
    }

    /**
     * @param asociado the asociado to set
     */
    public void setAsociado(Asociado asociado) {
        this.asociado = asociado;
    }





}

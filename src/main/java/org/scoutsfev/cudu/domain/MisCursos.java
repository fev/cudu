
package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;


@Entity
public class MisCursos implements Serializable {
	
    private static long serialVersionUID = 1L;

     @EmbeddedId
    private MisCursosPK misCursosPK;
        
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ronda;
  
    private String formacion;
    
    private String curso;
    
    private char calificacion;
    
    private int faltas;
    
    private char trabajo;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "fecha_entrega_trabajo")
    private Date fechaEntregaTrabajo;
   

    /**
     * @return the ronda
     */
    public Date getRonda() {
        return ronda;
    }

    /**
     * @param ronda the ronda to set
     */
    public void setRonda(Date ronda) {
        this.ronda = ronda;
    }

    /**
     * @return the formacion
     */
    public String getFormacion() {
        return formacion;
    }

    /**
     * @param formacion the formacion to set
     */
    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    /**
     * @return the curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(String curso) {
        this.curso = curso;
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
     * @return the faltas
     */
    public int getFaltas() {
        return faltas;
    }

    /**
     * @param faltas the faltas to set
     */
    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    /**
     * @return the misCursosPK
     */
    public MisCursosPK getMisCursosPK() {
        return misCursosPK;
    }

    /**
     * @param misCursosPK the misCursosPK to set
     */
    public void setMisCursosPK(MisCursosPK misCursosPK) {
        this.misCursosPK = misCursosPK;
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
     * @return the fechaEntregaTrabajo
     */
    public Date getFechaEntregaTrabajo() {
        return fechaEntregaTrabajo;
    }

    /**
     * @param fechaEntregaTrabajo the fechaEntregaTrabajo to set
     */
    public void setFechaEntregaTrabajo(Date fechaEntregaTrabajo) {
        this.fechaEntregaTrabajo = fechaEntregaTrabajo;
    }
 
    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
  
  
}

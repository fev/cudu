/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

/**
 *
 * @author gasp
 */
public class InscripcionesCurso {
    
    //private List monograficosElegidos;
    //private List<Integer> monograficosIds;
    private int idAsociado;
    private int idCurso;
    private String monograficosElegidos;
    
    private char formatoMateriales; 
    private char idiomaMateriales;
    private char modoContacto;

    
    public InscripcionesCurso(){
    }
    

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

    /**
     * @return the formatoMateriales
     */
    public char getFormatoMateriales() {
        return formatoMateriales;
    }

    /**
     * @param formatoMateriales the formatoMateriales to set
     */
    public void setFormatoMateriales(char formatoMateriales) {
        this.formatoMateriales = formatoMateriales;
    }

    /**
     * @return the idiomaMateriales
     */
    public char getIdiomaMateriales() {
        return idiomaMateriales;
    }

    /**
     * @param idiomaMateriales the idiomaMateriales to set
     */
    public void setIdiomaMateriales(char idiomaMateriales) {
        this.idiomaMateriales = idiomaMateriales;
    }

    /**
     * @return the modoContacto
     */
    public char getModoContacto() {
        return modoContacto;
    }

    /**
     * @param modoContacto the modoContacto to set
     */
    public void setModoContacto(char modoContacto) {
        this.modoContacto = modoContacto;
    }

    /**
     * @return the monograficosElegidos
     */
    public String getMonograficosElegidos() {
        return monograficosElegidos;
    }

    /**
     * @param monograficosElegidos the monograficosElegidos to set
     */
    public void setMonograficosElegidos(String monograficosElegidos) {
        this.monograficosElegidos = monograficosElegidos;
    }

}

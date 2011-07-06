/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

import java.util.Date;

/**
 *
 * @author gaxp
 */
public class Estadistica {
    
    private int grupos;
    private int voluntarios;
    private int asociados;
    private int jovenes;
    
    private int castores;
    private int lobatos;
    private int exploradores;
    private int pioneros;
    private int companys;
    
    private int scouters;
    private int comite;
    
    private String grupoMenosNumeroso;
    private int grupoMenosNumerosoCantidad;
    private String grupoMenosActualizado;
    private Date grupoMenosActualizadoFecha;

    /**
     * @return the grupos
     */
    public int getGrupos() {
        return grupos;
    }

    /**
     * @param grupos the grupos to set
     */
    public void setGrupos(int grupos) {
        this.grupos = grupos;
    }

    /**
     * @return the voluntarios
     */
    public int getVoluntarios() {
        return voluntarios;
    }

    /**
     * @param voluntarios the voluntarios to set
     */
    public void setVoluntarios(int voluntarios) {
        this.voluntarios = voluntarios;
    }

    /**
     * @return the asociados
     */
    public int getAsociados() {
        return asociados;
    }

    /**
     * @param asociados the asociados to set
     */
    public void setAsociados(int asociados) {
        this.asociados = asociados;
    }

    /**
     * @return the castores
     */
    public int getCastores() {
        return castores;
    }

    /**
     * @param castores the castores to set
     */
    public void setCastores(int castores) {
        this.castores = castores;
    }

    /**
     * @return the lobatos
     */
    public int getLobatos() {
        return lobatos;
    }

    /**
     * @param lobatos the lobatos to set
     */
    public void setLobatos(int lobatos) {
        this.lobatos = lobatos;
    }

    /**
     * @return the exploradores
     */
    public int getExploradores() {
        return exploradores;
    }

    /**
     * @param exploradores the exploradores to set
     */
    public void setExploradores(int exploradores) {
        this.exploradores = exploradores;
    }

    /**
     * @return the pioneros
     */
    public int getPioneros() {
        return pioneros;
    }

    /**
     * @param pioneros the pioneros to set
     */
    public void setPioneros(int pioneros) {
        this.pioneros = pioneros;
    }

    /**
     * @return the scouters
     */
    public int getScouters() {
        return scouters;
    }

    /**
     * @param scouters the scouters to set
     */
    public void setScouters(int scouters) {
        this.scouters = scouters;
    }

    /**
     * @return the comite
     */
    public int getComite() {
        return comite;
    }

    /**
     * @param comite the comite to set
     */
    public void setComite(int comite) {
        this.comite = comite;
    }

    /**
     * @return the grupoMenosNumeroso
     */
    public String getGrupoMenosNumeroso() {
        return grupoMenosNumeroso;
    }

    /**
     * @param grupoMenosNumeroso the grupoMenosNumeroso to set
     */
    public void setGrupoMenosNumeroso(String grupoMenosNumeroso) {
        this.grupoMenosNumeroso = grupoMenosNumeroso;
    }

    /**
     * @return the grupoMenosNumorosoCantidad
     */
    public int getGrupoMenosNumerosoCantidad() {
        return grupoMenosNumerosoCantidad;
    }

    /**
     * @param grupoMenosNumorosoCantidad the grupoMenosNumorosoCantidad to set
     */
    public void setGrupoMenosNumerosoCantidad(int grupoMenosNumorosoCantidad) {
        this.grupoMenosNumerosoCantidad = grupoMenosNumorosoCantidad;
    }

    /**
     * @return the grupoMenosActualizado
     */
    public String getGrupoMenosActualizado() {
        return grupoMenosActualizado;
    }

    /**
     * @param grupoMenosActualizado the grupoMenosActualizado to set
     */
    public void setGrupoMenosActualizado(String grupoMenosActualizado) {
        this.grupoMenosActualizado = grupoMenosActualizado;
    }

    /**
     * @return the grupoMenosActualizadoFecha
     */
    public Date getGrupoMenosActualizadoFecha() {
        return grupoMenosActualizadoFecha;
    }

    /**
     * @param grupoMenosActualizadoFecha the grupoMenosActualizadoFecha to set
     */
    public void setGrupoMenosActualizadoFecha(Date grupoMenosActualizadoFecha) {
        this.grupoMenosActualizadoFecha = grupoMenosActualizadoFecha;
    }

    /**
     * @return the companys
     */
    public int getCompanys() {
        return companys;
    }

    /**
     * @param companys the companys to set
     */
    public void setCompanys(int companys) {
        this.companys = companys;
    }
    
    public void setAsociados(){
        asociados = setVoluntarios()+ setJovenes();

    }
    public int setVoluntarios()
    {
        voluntarios = scouters+jovenes;
        return voluntarios;
    }
    public int setJovenes()
    {
        jovenes = castores+companys+lobatos+exploradores+pioneros;
        return jovenes;
    }

    /**
     * @return the jovenes
     */
    public int getJovenes() {
        return jovenes;
    }

    /**
     * @param jovenes the jovenes to set
     */
    public void setJovenes(int jovenes) {
        this.jovenes = jovenes;
    }
    
}

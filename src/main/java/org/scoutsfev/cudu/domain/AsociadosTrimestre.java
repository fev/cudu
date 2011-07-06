/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class AsociadosTrimestre {
    
    @Id
    private int anyo;
    
    @Id
    private int mes;
    
    private int castores;
    private int exploradores;
    private int lobatos;
    private int pioneros;
    private int companys;
    private int scouters;
    private int comite;
    
    private int asociados;
    private int voluntarios;
    private int jovenes;
    private int grupos;

    private String grupoMenosNumeroso;
    private int grupoMenosNumerosoCantidad;
    private String grupoMenosActualizado;
    
    private String idgrupo;

    @Temporal(javax.persistence.TemporalType.DATE)
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
     * @return the anyos
     */
    public int getAnyo() {
        return anyo;
    }

    /**
     * @param anyos the anyos to set
     */
    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    /**
     * @return the mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes) {
        this.mes = mes;
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
    
    
    public void setAsociados(){
        setAsociados( setVoluntarios()+ setJovenes());

    }
    public int setVoluntarios()
    {
        setVoluntarios( scouters+jovenes);
        return getVoluntarios();
    }
    public int setJovenes()
    {
        setJovenes( castores + companys + lobatos + exploradores + pioneros);
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
     * @return the grupoMenosNumerosoCantidad
     */
    public int getGrupoMenosNumerosoCantidad() {
        return grupoMenosNumerosoCantidad;
    }

    /**
     * @param grupoMenosNumerosoCantidad the grupoMenosNumerosoCantidad to set
     */
    public void setGrupoMenosNumerosoCantidad(int grupoMenosNumerosoCantidad) {
        this.grupoMenosNumerosoCantidad = grupoMenosNumerosoCantidad;
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
     * @return the idgrupo
     */
    public String getIdgrupo() {
        return idgrupo;
    }

    /**
     * @param idgrupo the idgrupo to set
     */
    public void setIdgrupo(String idgrupo) {
        this.idgrupo = idgrupo;
    }
}

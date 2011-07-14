package org.scoutsfev.cudu.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Indica en qué  cargos está el asociado.
 * Se mantiene la nomenglatura 'is', 'get' por convención. 
 */
@Embeddable
public class Cargo {
        
	@NotNull
	@Column(name = "cargo_Presidencia")
	private boolean presidencia;
        
	@NotNull
	@Column(name = "cargo_Tesoreria")
	private boolean tesoreria;
        
	@NotNull
	@Column(name = "cargo_Cocina")
	private boolean cocina;
        
	@NotNull
	@Column(name = "cargo_Otro")
	private boolean otro;
        
	@NotNull
	@Column(name = "cargo_Secretaria")
	private boolean secretaria;
        
	@NotNull
	@Column(name = "cargo_Consiliario")
	private boolean consiliario;
        
	@NotNull
	@Column(name = "cargo_Vocal")
	private boolean vocal;
        
        @NotNull
	@Column(name = "cargo_Intendencia")
	private boolean intendencia;


    /**
     * @return the presidencia
     */
    public boolean isPresidencia() {
        return presidencia;
    }

    /**
     * @param presidencia the presidencia to set
     */
    public void setPresidencia(boolean presidencia) {
        this.presidencia = presidencia;
    }

    /**
     * @return the tesoreria
     */
    public boolean isTesoreria() {
        return tesoreria;
    }

    /**
     * @param tesoreria the tesoreria to set
     */
    public void setTesoreria(boolean tesoreria) {
        this.tesoreria = tesoreria;
    }

    /**
     * @return the cocina
     */
    public boolean isCocina() {
        return cocina;
    }

    /**
     * @param cocina the cocina to set
     */
    public void setCocina(boolean cocina) {
        this.cocina = cocina;
    }

    /**
     * @return the otro
     */
    public boolean isOtro() {
        return otro;
    }

    /**
     * @param otro the otro to set
     */
    public void setOtro(boolean otro) {
        this.otro = otro;
    }

    /**
     * @return the secretaria
     */
    public boolean isSecretaria() {
        return secretaria;
    }

    /**
     * @param secretaria the secretaria to set
     */
    public void setSecretaria(boolean secretaria) {
        this.secretaria = secretaria;
    }

    /**
     * @return the consiliario
     */
    public boolean isConsiliario() {
        return consiliario;
    }

    /**
     * @param consiliario the consiliario to set
     */
    public void setConsiliario(boolean consiliario) {
        this.consiliario = consiliario;
    }

    /**
     * @return the vocal
     */
    public boolean isVocal() {
        return vocal;
    }

    /**
     * @param vocal the vocal to set
     */
    public void setVocal(boolean vocal) {
        this.vocal = vocal;
    }

    /**
     * @return the intendencia
     */
    public boolean isIntendencia() {
        return intendencia;
    }

    /**
     * @param intendencia the intendencia to set
     */
    public void setIntendencia(boolean intendencia) {
        this.intendencia = intendencia;
    }
}

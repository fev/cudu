package org.scoutsfev.cudu.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Indica en qué ramas está el asociado.
 * Se mantiene la nomenglatura 'is', 'get' por convención. 
 */
@Embeddable
public class Rama {

	@NotNull
	@Column(name = "rama_colonia")
	private boolean colonia;

	@NotNull
	@Column(name = "rama_manada")
	private boolean manada;

	@NotNull
	@Column(name = "rama_exploradores")
	private boolean exploradores;
	
	@NotNull
	@Column(name = "rama_pioneros")
	private boolean pioneros;
	
	@NotNull
	@Column(name = "rama_rutas")
	private boolean rutas;

	public boolean isColonia() {
		return colonia;
	}

	public void setColonia(boolean colonia) {
		this.colonia = colonia;
	}

	public boolean isManada() {
		return manada;
	}

	public void setManada(boolean manada) {
		this.manada = manada;
	}

	public boolean isExploradores() {
		return exploradores;
	}

	public void setExploradores(boolean exploradores) {
		this.exploradores = exploradores;
	}

	public boolean isPioneros() {
		return pioneros;
	}

	public void setPioneros(boolean pioneros) {
		this.pioneros = pioneros;
	}

	public boolean isRutas() {
		return rutas;
	}

	public void setRutas(boolean rutas) {
		this.rutas = rutas;
	}
}

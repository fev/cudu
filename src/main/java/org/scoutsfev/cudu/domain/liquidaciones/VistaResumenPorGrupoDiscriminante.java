package org.scoutsfev.cudu.domain.liquidaciones;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class VistaResumenPorGrupoDiscriminante implements Serializable {

	private static final long serialVersionUID = -7355228697691758636L;
	
	private Integer ejercicio;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(name = "grupo_id")
	private String grupoId;

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String idGrupo) {
		this.grupoId = idGrupo;
	}
	
	public boolean equals(Object anyObj) {
		if (this == anyObj) {
			return true;
		}
		if (!(anyObj instanceof VistaResumenPorGrupoDiscriminante)) {
			return false;
		}
		VistaResumenPorGrupoDiscriminante target = (VistaResumenPorGrupoDiscriminante)anyObj;
		return this.ejercicio.equals(target.ejercicio) 
			&& this.fecha.equals(target.fecha)
			&& this.grupoId.equals(target.grupoId);
    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ejercicio.hashCode();
		hash = hash * prime + this.fecha.hashCode();
		hash = hash * prime + this.grupoId.hashCode();
		return hash;
    }
}

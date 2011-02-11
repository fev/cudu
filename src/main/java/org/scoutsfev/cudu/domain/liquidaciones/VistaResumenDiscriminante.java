package org.scoutsfev.cudu.domain.liquidaciones;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class VistaResumenDiscriminante implements Serializable {

	private static final long serialVersionUID = -3006606646215932860L;
	
	private Integer ejercicio;

	@Temporal(TemporalType.DATE)
	private Date fecha;

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

	public boolean equals(Object anyObj) {
		if (this == anyObj) {
			return true;
		}
		if (!(anyObj instanceof VistaResumenDiscriminante)) {
			return false;
		}
		VistaResumenDiscriminante target = (VistaResumenDiscriminante)anyObj;
		return this.ejercicio.equals(target.ejercicio) 
			&& this.fecha.equals(target.fecha);
    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ejercicio.hashCode();
		hash = hash * prime + this.fecha.hashCode();
		return hash;
    }
}

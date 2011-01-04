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

}

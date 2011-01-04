package org.scoutsfev.cudu.domain.liquidaciones;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "liq_resumen")
@IdClass(VistaResumenDiscriminante.class)
@NamedQuery(name = "liquidacionesDeAsociacion", query = "SELECT r FROM VistaResumen r WHERE asociacion = :asociacion ORDER BY fecha desc")
public class VistaResumen implements Serializable {

	private static final long serialVersionUID = -2804792829304451084L;
	
	@Id
	protected Integer ejercicio;

	@Id
	@Temporal(TemporalType.DATE)
	protected Date fecha;

	protected Long altas;

	protected Long bajas;
	
	protected Integer asociacion;

	public VistaResumen() {
	}

	public Long getAltas() {
		return this.altas;
	}

	public void setAltas(Long altas) {
		this.altas = altas;
	}

	public Integer getAsociacion() {
		return this.asociacion;
	}

	public void setAsociacion(Integer asociacion) {
		this.asociacion = asociacion;
	}

	public Long getBajas() {
		return this.bajas;
	}

	public void setBajas(Long bajas) {
		this.bajas = bajas;
	}

	public Integer getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
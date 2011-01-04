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
}

package org.scoutsfev.cudu.domain.liquidaciones;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "liq_detalle_grupo")
@IdClass(VistaResumenPorGrupoDiscriminante.class)
public class VistaResumenPorGrupo implements Serializable {

	private static final long serialVersionUID = -5417462324761587176L;

	@Id
	private Integer ejercicio;

	@Id
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Id
	@Column(name = "grupo_id")
	private String grupoId;

	@Column(name = "grupo_nombre")
	private String grupoNombre;

	private char caracter;

	private long cantidad;

	private int asociacion;

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getGrupoNombre() {
		return grupoNombre;
	}

	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	public char getCaracter() {
		return caracter;
	}

	public void setCaracter(char caracter) {
		this.caracter = caracter;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public int getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(int asociacion) {
		this.asociacion = asociacion;
	}
}

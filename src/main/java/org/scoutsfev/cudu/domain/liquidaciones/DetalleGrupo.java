package org.scoutsfev.cudu.domain.liquidaciones;

import java.io.Serializable;

public class DetalleGrupo implements Serializable {
	
	private static final long serialVersionUID = -6875652366813936793L;

	protected String id;
	protected String nombre;
	protected long altas;
	protected long bajas;

	public DetalleGrupo() {
	}

	public DetalleGrupo(String id, String nombre, int altas, int bajas) {
		this.id = id;
		this.nombre = nombre;
		this.altas = altas;
		this.bajas = bajas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getAltas() {
		return altas;
	}

	public void setAltas(long altas) {
		this.altas = altas;
	}

	public long getBajas() {
		return bajas;
	}

	public void setBajas(long bajas) {
		this.bajas = bajas;
	}
}

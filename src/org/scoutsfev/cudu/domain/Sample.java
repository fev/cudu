package org.scoutsfev.cudu.domain;

import java.io.Serializable;

public class Sample implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;

	public Sample(String nombre) {
		this.nombre = nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}

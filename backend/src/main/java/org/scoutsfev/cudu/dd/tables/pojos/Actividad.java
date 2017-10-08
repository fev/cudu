/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Actividad implements Serializable {

	private static final long serialVersionUID = -752166539;

	private final Integer   id;
	private final String    grupoId;
	private final String    nombre;
	private final Date      fechaInicio;
	private final Date      fechaFin;
	private final String    creadaPor;
	private final String    lugar;
	private final String    precio;
	private final String    responsable;
	private final String    notas;
	private final Boolean   ramaColonia;
	private final Boolean   ramaExpedicion;
	private final Boolean   ramaExploradores;
	private final Boolean   ramaManada;
	private final Boolean   ramaRuta;
	private final Timestamp fechaCreacion;
	private final Timestamp fechaBaja;

	public Actividad(Actividad value) {
		this.id = value.id;
		this.grupoId = value.grupoId;
		this.nombre = value.nombre;
		this.fechaInicio = value.fechaInicio;
		this.fechaFin = value.fechaFin;
		this.creadaPor = value.creadaPor;
		this.lugar = value.lugar;
		this.precio = value.precio;
		this.responsable = value.responsable;
		this.notas = value.notas;
		this.ramaColonia = value.ramaColonia;
		this.ramaExpedicion = value.ramaExpedicion;
		this.ramaExploradores = value.ramaExploradores;
		this.ramaManada = value.ramaManada;
		this.ramaRuta = value.ramaRuta;
		this.fechaCreacion = value.fechaCreacion;
		this.fechaBaja = value.fechaBaja;
	}

	public Actividad(
		Integer   id,
		String    grupoId,
		String    nombre,
		Date      fechaInicio,
		Date      fechaFin,
		String    creadaPor,
		String    lugar,
		String    precio,
		String    responsable,
		String    notas,
		Boolean   ramaColonia,
		Boolean   ramaExpedicion,
		Boolean   ramaExploradores,
		Boolean   ramaManada,
		Boolean   ramaRuta,
		Timestamp fechaCreacion,
		Timestamp fechaBaja
	) {
		this.id = id;
		this.grupoId = grupoId;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.creadaPor = creadaPor;
		this.lugar = lugar;
		this.precio = precio;
		this.responsable = responsable;
		this.notas = notas;
		this.ramaColonia = ramaColonia;
		this.ramaExpedicion = ramaExpedicion;
		this.ramaExploradores = ramaExploradores;
		this.ramaManada = ramaManada;
		this.ramaRuta = ramaRuta;
		this.fechaCreacion = fechaCreacion;
		this.fechaBaja = fechaBaja;
	}

	public Integer getId() {
		return this.id;
	}

	public String getGrupoId() {
		return this.grupoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public String getCreadaPor() {
		return this.creadaPor;
	}

	public String getLugar() {
		return this.lugar;
	}

	public String getPrecio() {
		return this.precio;
	}

	public String getResponsable() {
		return this.responsable;
	}

	public String getNotas() {
		return this.notas;
	}

	public Boolean getRamaColonia() {
		return this.ramaColonia;
	}

	public Boolean getRamaExpedicion() {
		return this.ramaExpedicion;
	}

	public Boolean getRamaExploradores() {
		return this.ramaExploradores;
	}

	public Boolean getRamaManada() {
		return this.ramaManada;
	}

	public Boolean getRamaRuta() {
		return this.ramaRuta;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public Timestamp getFechaBaja() {
		return this.fechaBaja;
	}
}

/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.pojos;


import java.io.Serializable;

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
public class DtoEstadoInscripcion implements Serializable {

	private static final long serialVersionUID = -702165153;

	private final Integer cursoId;
	private final Integer asociadoId;
	private final Integer plazas;
	private final Integer inscritos;
	private final Integer disponibles;
	private final Long    orden;

	public DtoEstadoInscripcion(DtoEstadoInscripcion value) {
		this.cursoId = value.cursoId;
		this.asociadoId = value.asociadoId;
		this.plazas = value.plazas;
		this.inscritos = value.inscritos;
		this.disponibles = value.disponibles;
		this.orden = value.orden;
	}

	public DtoEstadoInscripcion(
		Integer cursoId,
		Integer asociadoId,
		Integer plazas,
		Integer inscritos,
		Integer disponibles,
		Long    orden
	) {
		this.cursoId = cursoId;
		this.asociadoId = asociadoId;
		this.plazas = plazas;
		this.inscritos = inscritos;
		this.disponibles = disponibles;
		this.orden = orden;
	}

	public Integer getCursoId() {
		return this.cursoId;
	}

	public Integer getAsociadoId() {
		return this.asociadoId;
	}

	public Integer getPlazas() {
		return this.plazas;
	}

	public Integer getInscritos() {
		return this.inscritos;
	}

	public Integer getDisponibles() {
		return this.disponibles;
	}

	public Long getOrden() {
		return this.orden;
	}
}

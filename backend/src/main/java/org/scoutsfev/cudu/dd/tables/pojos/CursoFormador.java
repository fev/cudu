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
public class CursoFormador implements Serializable {

	private static final long serialVersionUID = -83038083;

	private final Integer cursoId;
	private final Integer asociadoId;

	public CursoFormador(CursoFormador value) {
		this.cursoId = value.cursoId;
		this.asociadoId = value.asociadoId;
	}

	public CursoFormador(
		Integer cursoId,
		Integer asociadoId
	) {
		this.cursoId = cursoId;
		this.asociadoId = asociadoId;
	}

	public Integer getCursoId() {
		return this.cursoId;
	}

	public Integer getAsociadoId() {
		return this.asociadoId;
	}
}

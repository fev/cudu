/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;
import org.scoutsfev.cudu.db.tables.DtoEstadoInscripcion;


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
public class DtoEstadoInscripcionRecord extends TableRecordImpl<DtoEstadoInscripcionRecord> implements Record4<Integer, Integer, Integer, Long> {

	private static final long serialVersionUID = -580018093;

	/**
	 * Setter for <code>public.dto_estado_inscripcion.curso_id</code>.
	 */
	public void setCursoId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.dto_estado_inscripcion.curso_id</code>.
	 */
	public Integer getCursoId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.dto_estado_inscripcion.plazas</code>.
	 */
	public void setPlazas(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.dto_estado_inscripcion.plazas</code>.
	 */
	public Integer getPlazas() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>public.dto_estado_inscripcion.asociado_id</code>.
	 */
	public void setAsociadoId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.dto_estado_inscripcion.asociado_id</code>.
	 */
	public Integer getAsociadoId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>public.dto_estado_inscripcion.orden</code>.
	 */
	public void setOrden(Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.dto_estado_inscripcion.orden</code>.
	 */
	public Long getOrden() {
		return (Long) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, Integer, Integer, Long> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, Integer, Integer, Long> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION.CURSO_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION.PLAZAS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION.ASOCIADO_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field4() {
		return DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION.ORDEN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getCursoId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getPlazas();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getAsociadoId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value4() {
		return getOrden();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoEstadoInscripcionRecord value1(Integer value) {
		setCursoId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoEstadoInscripcionRecord value2(Integer value) {
		setPlazas(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoEstadoInscripcionRecord value3(Integer value) {
		setAsociadoId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoEstadoInscripcionRecord value4(Long value) {
		setOrden(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoEstadoInscripcionRecord values(Integer value1, Integer value2, Integer value3, Long value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DtoEstadoInscripcionRecord
	 */
	public DtoEstadoInscripcionRecord() {
		super(DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION);
	}

	/**
	 * Create a detached, initialised DtoEstadoInscripcionRecord
	 */
	public DtoEstadoInscripcionRecord(Integer cursoId, Integer plazas, Integer asociadoId, Long orden) {
		super(DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION);

		setValue(0, cursoId);
		setValue(1, plazas);
		setValue(2, asociadoId);
		setValue(3, orden);
	}
}
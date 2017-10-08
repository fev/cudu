/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;
import org.scoutsfev.cudu.dd.tables.DtoDatosGrupoTipo;


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
public class DtoDatosGrupoTipoRecord extends TableRecordImpl<DtoDatosGrupoTipoRecord> implements Record4<String, Long, Long, Long> {

	private static final long serialVersionUID = -494983139;

	/**
	 * Setter for <code>public.dto_datos_grupo_tipo.grupo_id</code>.
	 */
	public void setGrupoId(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.dto_datos_grupo_tipo.grupo_id</code>.
	 */
	public String getGrupoId() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>public.dto_datos_grupo_tipo.joven</code>.
	 */
	public void setJoven(Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.dto_datos_grupo_tipo.joven</code>.
	 */
	public Long getJoven() {
		return (Long) getValue(1);
	}

	/**
	 * Setter for <code>public.dto_datos_grupo_tipo.kraal</code>.
	 */
	public void setKraal(Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.dto_datos_grupo_tipo.kraal</code>.
	 */
	public Long getKraal() {
		return (Long) getValue(2);
	}

	/**
	 * Setter for <code>public.dto_datos_grupo_tipo.comite</code>.
	 */
	public void setComite(Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.dto_datos_grupo_tipo.comite</code>.
	 */
	public Long getComite() {
		return (Long) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<String, Long, Long, Long> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<String, Long, Long, Long> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return DtoDatosGrupoTipo.DTO_DATOS_GRUPO_TIPO.GRUPO_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return DtoDatosGrupoTipo.DTO_DATOS_GRUPO_TIPO.JOVEN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field3() {
		return DtoDatosGrupoTipo.DTO_DATOS_GRUPO_TIPO.KRAAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field4() {
		return DtoDatosGrupoTipo.DTO_DATOS_GRUPO_TIPO.COMITE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getGrupoId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value2() {
		return getJoven();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value3() {
		return getKraal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value4() {
		return getComite();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoDatosGrupoTipoRecord value1(String value) {
		setGrupoId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoDatosGrupoTipoRecord value2(Long value) {
		setJoven(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoDatosGrupoTipoRecord value3(Long value) {
		setKraal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoDatosGrupoTipoRecord value4(Long value) {
		setComite(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoDatosGrupoTipoRecord values(String value1, Long value2, Long value3, Long value4) {
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
	 * Create a detached DtoDatosGrupoTipoRecord
	 */
	public DtoDatosGrupoTipoRecord() {
		super(DtoDatosGrupoTipo.DTO_DATOS_GRUPO_TIPO);
	}

	/**
	 * Create a detached, initialised DtoDatosGrupoTipoRecord
	 */
	public DtoDatosGrupoTipoRecord(String grupoId, Long joven, Long kraal, Long comite) {
		super(DtoDatosGrupoTipo.DTO_DATOS_GRUPO_TIPO);

		setValue(0, grupoId);
		setValue(1, joven);
		setValue(2, kraal);
		setValue(3, comite);
	}
}

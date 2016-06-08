/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.db.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.scoutsfev.cudu.db.tables.LiquidacionAsociado;


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
public class LiquidacionAsociadoRecord extends UpdatableRecordImpl<LiquidacionAsociadoRecord> implements Record4<Integer, Integer, Boolean, Timestamp> {

	private static final long serialVersionUID = 76025985;

	/**
	 * Setter for <code>public.liquidacion_asociado.liquidacion_id</code>.
	 */
	public void setLiquidacionId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.liquidacion_asociado.liquidacion_id</code>.
	 */
	public Integer getLiquidacionId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.liquidacion_asociado.asociado_id</code>.
	 */
	public void setAsociadoId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.liquidacion_asociado.asociado_id</code>.
	 */
	public Integer getAsociadoId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>public.liquidacion_asociado.alta</code>.
	 */
	public void setAlta(Boolean value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.liquidacion_asociado.alta</code>.
	 */
	public Boolean getAlta() {
		return (Boolean) getValue(2);
	}

	/**
	 * Setter for <code>public.liquidacion_asociado.creado_en</code>.
	 */
	public void setCreadoEn(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.liquidacion_asociado.creado_en</code>.
	 */
	public Timestamp getCreadoEn() {
		return (Timestamp) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record2<Integer, Integer> key() {
		return (Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, Integer, Boolean, Timestamp> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, Integer, Boolean, Timestamp> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return LiquidacionAsociado.LIQUIDACION_ASOCIADO.LIQUIDACION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return LiquidacionAsociado.LIQUIDACION_ASOCIADO.ASOCIADO_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field3() {
		return LiquidacionAsociado.LIQUIDACION_ASOCIADO.ALTA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return LiquidacionAsociado.LIQUIDACION_ASOCIADO.CREADO_EN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getLiquidacionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getAsociadoId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value3() {
		return getAlta();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getCreadoEn();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionAsociadoRecord value1(Integer value) {
		setLiquidacionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionAsociadoRecord value2(Integer value) {
		setAsociadoId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionAsociadoRecord value3(Boolean value) {
		setAlta(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionAsociadoRecord value4(Timestamp value) {
		setCreadoEn(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionAsociadoRecord values(Integer value1, Integer value2, Boolean value3, Timestamp value4) {
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
	 * Create a detached LiquidacionAsociadoRecord
	 */
	public LiquidacionAsociadoRecord() {
		super(LiquidacionAsociado.LIQUIDACION_ASOCIADO);
	}

	/**
	 * Create a detached, initialised LiquidacionAsociadoRecord
	 */
	public LiquidacionAsociadoRecord(Integer liquidacionId, Integer asociadoId, Boolean alta, Timestamp creadoEn) {
		super(LiquidacionAsociado.LIQUIDACION_ASOCIADO);

		setValue(0, liquidacionId);
		setValue(1, asociadoId);
		setValue(2, alta);
		setValue(3, creadoEn);
	}
}
/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.records;


import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;
import org.scoutsfev.cudu.dd.tables.Liquidacion;


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
public class LiquidacionRecord extends UpdatableRecordImpl<LiquidacionRecord> implements Record11<Integer, String, Short, Boolean, BigDecimal, BigDecimal, Timestamp, BigDecimal, String, Timestamp, String> {

	private static final long serialVersionUID = -1873681143;

	/**
	 * Setter for <code>public.liquidacion.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.liquidacion.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.liquidacion.grupo_id</code>.
	 */
	public void setGrupoId(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.liquidacion.grupo_id</code>.
	 */
	public String getGrupoId() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.liquidacion.ronda_id</code>.
	 */
	public void setRondaId(Short value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.liquidacion.ronda_id</code>.
	 */
	public Short getRondaId() {
		return (Short) getValue(2);
	}

	/**
	 * Setter for <code>public.liquidacion.borrador</code>.
	 */
	public void setBorrador(Boolean value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.liquidacion.borrador</code>.
	 */
	public Boolean getBorrador() {
		return (Boolean) getValue(3);
	}

	/**
	 * Setter for <code>public.liquidacion.ajuste_manual</code>.
	 */
	public void setAjusteManual(BigDecimal value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.liquidacion.ajuste_manual</code>.
	 */
	public BigDecimal getAjusteManual() {
		return (BigDecimal) getValue(4);
	}

	/**
	 * Setter for <code>public.liquidacion.pagado</code>.
	 */
	public void setPagado(BigDecimal value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.liquidacion.pagado</code>.
	 */
	public BigDecimal getPagado() {
		return (BigDecimal) getValue(5);
	}

	/**
	 * Setter for <code>public.liquidacion.fecha_pago</code>.
	 */
	public void setFechaPago(Timestamp value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>public.liquidacion.fecha_pago</code>.
	 */
	public Timestamp getFechaPago() {
		return (Timestamp) getValue(6);
	}

	/**
	 * Setter for <code>public.liquidacion.precio_unitario</code>.
	 */
	public void setPrecioUnitario(BigDecimal value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>public.liquidacion.precio_unitario</code>.
	 */
	public BigDecimal getPrecioUnitario() {
		return (BigDecimal) getValue(7);
	}

	/**
	 * Setter for <code>public.liquidacion.creado_por</code>.
	 */
	public void setCreadoPor(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>public.liquidacion.creado_por</code>.
	 */
	public String getCreadoPor() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>public.liquidacion.creado_en</code>.
	 */
	public void setCreadoEn(Timestamp value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>public.liquidacion.creado_en</code>.
	 */
	public Timestamp getCreadoEn() {
		return (Timestamp) getValue(9);
	}

	/**
	 * Setter for <code>public.liquidacion.observaciones</code>.
	 */
	public void setObservaciones(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>public.liquidacion.observaciones</code>.
	 */
	public String getObservaciones() {
		return (String) getValue(10);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record11 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, String, Short, Boolean, BigDecimal, BigDecimal, Timestamp, BigDecimal, String, Timestamp, String> fieldsRow() {
		return (Row11) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, String, Short, Boolean, BigDecimal, BigDecimal, Timestamp, BigDecimal, String, Timestamp, String> valuesRow() {
		return (Row11) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Liquidacion.LIQUIDACION.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Liquidacion.LIQUIDACION.GRUPO_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Short> field3() {
		return Liquidacion.LIQUIDACION.RONDA_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field4() {
		return Liquidacion.LIQUIDACION.BORRADOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field5() {
		return Liquidacion.LIQUIDACION.AJUSTE_MANUAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field6() {
		return Liquidacion.LIQUIDACION.PAGADO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field7() {
		return Liquidacion.LIQUIDACION.FECHA_PAGO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field8() {
		return Liquidacion.LIQUIDACION.PRECIO_UNITARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return Liquidacion.LIQUIDACION.CREADO_POR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field10() {
		return Liquidacion.LIQUIDACION.CREADO_EN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return Liquidacion.LIQUIDACION.OBSERVACIONES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getGrupoId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Short value3() {
		return getRondaId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value4() {
		return getBorrador();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value5() {
		return getAjusteManual();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value6() {
		return getPagado();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value7() {
		return getFechaPago();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value8() {
		return getPrecioUnitario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getCreadoPor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value10() {
		return getCreadoEn();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getObservaciones();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value2(String value) {
		setGrupoId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value3(Short value) {
		setRondaId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value4(Boolean value) {
		setBorrador(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value5(BigDecimal value) {
		setAjusteManual(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value6(BigDecimal value) {
		setPagado(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value7(Timestamp value) {
		setFechaPago(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value8(BigDecimal value) {
		setPrecioUnitario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value9(String value) {
		setCreadoPor(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value10(Timestamp value) {
		setCreadoEn(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord value11(String value) {
		setObservaciones(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionRecord values(Integer value1, String value2, Short value3, Boolean value4, BigDecimal value5, BigDecimal value6, Timestamp value7, BigDecimal value8, String value9, Timestamp value10, String value11) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached LiquidacionRecord
	 */
	public LiquidacionRecord() {
		super(Liquidacion.LIQUIDACION);
	}

	/**
	 * Create a detached, initialised LiquidacionRecord
	 */
	public LiquidacionRecord(Integer id, String grupoId, Short rondaId, Boolean borrador, BigDecimal ajusteManual, BigDecimal pagado, Timestamp fechaPago, BigDecimal precioUnitario, String creadoPor, Timestamp creadoEn, String observaciones) {
		super(Liquidacion.LIQUIDACION);

		setValue(0, id);
		setValue(1, grupoId);
		setValue(2, rondaId);
		setValue(3, borrador);
		setValue(4, ajusteManual);
		setValue(5, pagado);
		setValue(6, fechaPago);
		setValue(7, precioUnitario);
		setValue(8, creadoPor);
		setValue(9, creadoEn);
		setValue(10, observaciones);
	}
}

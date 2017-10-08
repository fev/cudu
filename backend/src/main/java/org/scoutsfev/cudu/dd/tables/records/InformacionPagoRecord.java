/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.scoutsfev.cudu.dd.tables.InformacionPago;


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
public class InformacionPagoRecord extends UpdatableRecordImpl<InformacionPagoRecord> implements Record4<Integer, String, String, BigDecimal> {

	private static final long serialVersionUID = 715958500;

	/**
	 * Setter for <code>public.informacion_pago.asociacionid</code>.
	 */
	public void setAsociacionid(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.informacion_pago.asociacionid</code>.
	 */
	public Integer getAsociacionid() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.informacion_pago.titular</code>.
	 */
	public void setTitular(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.informacion_pago.titular</code>.
	 */
	public String getTitular() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.informacion_pago.iban</code>.
	 */
	public void setIban(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.informacion_pago.iban</code>.
	 */
	public String getIban() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.informacion_pago.precioporasociado</code>.
	 */
	public void setPrecioporasociado(BigDecimal value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.informacion_pago.precioporasociado</code>.
	 */
	public BigDecimal getPrecioporasociado() {
		return (BigDecimal) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, String, String, BigDecimal> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, String, String, BigDecimal> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return InformacionPago.INFORMACION_PAGO.ASOCIACIONID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return InformacionPago.INFORMACION_PAGO.TITULAR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return InformacionPago.INFORMACION_PAGO.IBAN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field4() {
		return InformacionPago.INFORMACION_PAGO.PRECIOPORASOCIADO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getAsociacionid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getTitular();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getIban();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value4() {
		return getPrecioporasociado();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InformacionPagoRecord value1(Integer value) {
		setAsociacionid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InformacionPagoRecord value2(String value) {
		setTitular(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InformacionPagoRecord value3(String value) {
		setIban(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InformacionPagoRecord value4(BigDecimal value) {
		setPrecioporasociado(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InformacionPagoRecord values(Integer value1, String value2, String value3, BigDecimal value4) {
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
	 * Create a detached InformacionPagoRecord
	 */
	public InformacionPagoRecord() {
		super(InformacionPago.INFORMACION_PAGO);
	}

	/**
	 * Create a detached, initialised InformacionPagoRecord
	 */
	public InformacionPagoRecord(Integer asociacionid, String titular, String iban, BigDecimal precioporasociado) {
		super(InformacionPago.INFORMACION_PAGO);

		setValue(0, asociacionid);
		setValue(1, titular);
		setValue(2, iban);
		setValue(3, precioporasociado);
	}
}

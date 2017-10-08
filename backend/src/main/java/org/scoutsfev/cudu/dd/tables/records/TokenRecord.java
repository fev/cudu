/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.scoutsfev.cudu.dd.tables.Token;


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
public class TokenRecord extends UpdatableRecordImpl<TokenRecord> implements Record4<String, Timestamp, Long, String> {

	private static final long serialVersionUID = 755399870;

	/**
	 * Setter for <code>public.token.token</code>.
	 */
	public void setToken(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.token.token</code>.
	 */
	public String getToken() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>public.token.creado</code>.
	 */
	public void setCreado(Timestamp value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.token.creado</code>.
	 */
	public Timestamp getCreado() {
		return (Timestamp) getValue(1);
	}

	/**
	 * Setter for <code>public.token.duracion_en_segundos</code>.
	 */
	public void setDuracionEnSegundos(Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.token.duracion_en_segundos</code>.
	 */
	public Long getDuracionEnSegundos() {
		return (Long) getValue(2);
	}

	/**
	 * Setter for <code>public.token.email</code>.
	 */
	public void setEmail(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.token.email</code>.
	 */
	public String getEmail() {
		return (String) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<String> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<String, Timestamp, Long, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<String, Timestamp, Long, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return Token.TOKEN.TOKEN_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field2() {
		return Token.TOKEN.CREADO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field3() {
		return Token.TOKEN.DURACION_EN_SEGUNDOS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Token.TOKEN.EMAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getToken();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value2() {
		return getCreado();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value3() {
		return getDuracionEnSegundos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getEmail();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenRecord value1(String value) {
		setToken(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenRecord value2(Timestamp value) {
		setCreado(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenRecord value3(Long value) {
		setDuracionEnSegundos(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenRecord value4(String value) {
		setEmail(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenRecord values(String value1, Timestamp value2, Long value3, String value4) {
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
	 * Create a detached TokenRecord
	 */
	public TokenRecord() {
		super(Token.TOKEN);
	}

	/**
	 * Create a detached, initialised TokenRecord
	 */
	public TokenRecord(String token, Timestamp creado, Long duracionEnSegundos, String email) {
		super(Token.TOKEN);

		setValue(0, token);
		setValue(1, creado);
		setValue(2, duracionEnSegundos);
		setValue(3, email);
	}
}

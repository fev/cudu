/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.db.routines;


import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.scoutsfev.cudu.db.Public;


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
public class Edad extends AbstractRoutine<Double> {

	private static final long serialVersionUID = -193709695;

	/**
	 * The parameter <code>public.edad.RETURN_VALUE</code>.
	 */
	public static final Parameter<Double> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.DOUBLE, false);

	/**
	 * The parameter <code>public.edad._1</code>.
	 */
	public static final Parameter<Date> _1 = createParameter("_1", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * Create a new routine call instance
	 */
	public Edad() {
		super("edad", Public.PUBLIC, org.jooq.impl.SQLDataType.DOUBLE);

		setReturnParameter(RETURN_VALUE);
		addInParameter(_1);
	}

	/**
	 * Set the <code>_1</code> parameter IN value to the routine
	 */
	public void set__1(Date value) {
		setValue(_1, value);
	}

	/**
	 * Set the <code>_1</code> parameter to the function to be used with a {@link org.jooq.Select} statement
	 */
	public void set__1(Field<Date> field) {
		setField(_1, field);
	}
}

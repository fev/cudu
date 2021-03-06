/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.db;


import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.AggregateFunction;
import org.jooq.Configuration;
import org.jooq.Field;
import org.scoutsfev.cudu.db.routines.CrearLiquidacion;
import org.scoutsfev.cudu.db.routines.Edad;
import org.scoutsfev.cudu.db.routines.Last;
import org.scoutsfev.cudu.db.routines.LastAgg;


/**
 * Convenience access to all stored procedures and functions in public
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Routines {

	/**
	 * Call <code>public.crear_liquidacion</code>
	 */
	public static Integer crearLiquidacion(Configuration configuration, String grupoId, Short rondaId, String creadoPor) {
		CrearLiquidacion f = new CrearLiquidacion();
		f.setGrupoId(grupoId);
		f.setRondaId(rondaId);
		f.setCreadoPor(creadoPor);

		f.execute(configuration);
		return f.getReturnValue();
	}

	/**
	 * Get <code>public.crear_liquidacion</code> as a field
	 */
	public static Field<Integer> crearLiquidacion(String grupoId, Short rondaId, String creadoPor) {
		CrearLiquidacion f = new CrearLiquidacion();
		f.setGrupoId(grupoId);
		f.setRondaId(rondaId);
		f.setCreadoPor(creadoPor);

		return f.asField();
	}

	/**
	 * Get <code>public.crear_liquidacion</code> as a field
	 */
	public static Field<Integer> crearLiquidacion(Field<String> grupoId, Field<Short> rondaId, Field<String> creadoPor) {
		CrearLiquidacion f = new CrearLiquidacion();
		f.setGrupoId(grupoId);
		f.setRondaId(rondaId);
		f.setCreadoPor(creadoPor);

		return f.asField();
	}

	/**
	 * Call <code>public.edad</code>
	 */
	public static Double edad(Configuration configuration, Date __1) {
		Edad f = new Edad();
		f.set__1(__1);

		f.execute(configuration);
		return f.getReturnValue();
	}

	/**
	 * Get <code>public.edad</code> as a field
	 */
	public static Field<Double> edad(Date __1) {
		Edad f = new Edad();
		f.set__1(__1);

		return f.asField();
	}

	/**
	 * Get <code>public.edad</code> as a field
	 */
	public static Field<Double> edad(Field<Date> __1) {
		Edad f = new Edad();
		f.set__1(__1);

		return f.asField();
	}

	/**
	 * Get <code>public.last</code> as a field
	 */
	public static AggregateFunction<Object> last(Object __1) {
		Last f = new Last();
		f.set__1(__1);

		return f.asAggregateFunction();
	}

	/**
	 * Get <code>public.last</code> as a field
	 */
	public static AggregateFunction<Object> last(Field<Object> __1) {
		Last f = new Last();
		f.set__1(__1);

		return f.asAggregateFunction();
	}

	/**
	 * Call <code>public.last_agg</code>
	 */
	public static Object lastAgg(Configuration configuration, Object __1, Object __2) {
		LastAgg f = new LastAgg();
		f.set__1(__1);
		f.set__2(__2);

		f.execute(configuration);
		return f.getReturnValue();
	}

	/**
	 * Get <code>public.last_agg</code> as a field
	 */
	public static Field<Object> lastAgg(Object __1, Object __2) {
		LastAgg f = new LastAgg();
		f.set__1(__1);
		f.set__2(__2);

		return f.asField();
	}

	/**
	 * Get <code>public.last_agg</code> as a field
	 */
	public static Field<Object> lastAgg(Field<Object> __1, Field<Object> __2) {
		LastAgg f = new LastAgg();
		f.set__1(__1);
		f.set__2(__2);

		return f.asField();
	}
}

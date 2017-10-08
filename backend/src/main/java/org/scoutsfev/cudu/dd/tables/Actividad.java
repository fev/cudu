/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.scoutsfev.cudu.dd.Keys;
import org.scoutsfev.cudu.dd.Public;
import org.scoutsfev.cudu.dd.tables.records.ActividadRecord;


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
public class Actividad extends TableImpl<ActividadRecord> {

	private static final long serialVersionUID = -2040355882;

	/**
	 * The reference instance of <code>public.actividad</code>
	 */
	public static final Actividad ACTIVIDAD = new Actividad();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ActividadRecord> getRecordType() {
		return ActividadRecord.class;
	}

	/**
	 * The column <code>public.actividad.id</code>.
	 */
	public final TableField<ActividadRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.actividad.grupo_id</code>.
	 */
	public final TableField<ActividadRecord, String> GRUPO_ID = createField("grupo_id", org.jooq.impl.SQLDataType.VARCHAR.length(3).nullable(false), this, "");

	/**
	 * The column <code>public.actividad.nombre</code>.
	 */
	public final TableField<ActividadRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>public.actividad.fecha_inicio</code>.
	 */
	public final TableField<ActividadRecord, Date> FECHA_INICIO = createField("fecha_inicio", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

	/**
	 * The column <code>public.actividad.fecha_fin</code>.
	 */
	public final TableField<ActividadRecord, Date> FECHA_FIN = createField("fecha_fin", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>public.actividad.creada_por</code>.
	 */
	public final TableField<ActividadRecord, String> CREADA_POR = createField("creada_por", org.jooq.impl.SQLDataType.VARCHAR.length(130).nullable(false), this, "");

	/**
	 * The column <code>public.actividad.lugar</code>.
	 */
	public final TableField<ActividadRecord, String> LUGAR = createField("lugar", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.actividad.precio</code>.
	 */
	public final TableField<ActividadRecord, String> PRECIO = createField("precio", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>public.actividad.responsable</code>.
	 */
	public final TableField<ActividadRecord, String> RESPONSABLE = createField("responsable", org.jooq.impl.SQLDataType.VARCHAR.length(130), this, "");

	/**
	 * The column <code>public.actividad.notas</code>.
	 */
	public final TableField<ActividadRecord, String> NOTAS = createField("notas", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>public.actividad.rama_colonia</code>.
	 */
	public final TableField<ActividadRecord, Boolean> RAMA_COLONIA = createField("rama_colonia", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.actividad.rama_expedicion</code>.
	 */
	public final TableField<ActividadRecord, Boolean> RAMA_EXPEDICION = createField("rama_expedicion", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.actividad.rama_exploradores</code>.
	 */
	public final TableField<ActividadRecord, Boolean> RAMA_EXPLORADORES = createField("rama_exploradores", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.actividad.rama_manada</code>.
	 */
	public final TableField<ActividadRecord, Boolean> RAMA_MANADA = createField("rama_manada", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.actividad.rama_ruta</code>.
	 */
	public final TableField<ActividadRecord, Boolean> RAMA_RUTA = createField("rama_ruta", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.actividad.fecha_creacion</code>.
	 */
	public final TableField<ActividadRecord, Timestamp> FECHA_CREACION = createField("fecha_creacion", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.actividad.fecha_baja</code>.
	 */
	public final TableField<ActividadRecord, Timestamp> FECHA_BAJA = createField("fecha_baja", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * Create a <code>public.actividad</code> table reference
	 */
	public Actividad() {
		this("actividad", null);
	}

	/**
	 * Create an aliased <code>public.actividad</code> table reference
	 */
	public Actividad(String alias) {
		this(alias, ACTIVIDAD);
	}

	private Actividad(String alias, Table<ActividadRecord> aliased) {
		this(alias, aliased, null);
	}

	private Actividad(String alias, Table<ActividadRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ActividadRecord, Integer> getIdentity() {
		return Keys.IDENTITY_ACTIVIDAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ActividadRecord> getPrimaryKey() {
		return Keys.ACTIVIDAD_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ActividadRecord>> getKeys() {
		return Arrays.<UniqueKey<ActividadRecord>>asList(Keys.ACTIVIDAD_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<ActividadRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<ActividadRecord, ?>>asList(Keys.ACTIVIDAD__ACTIVIDAD_GRUPO_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Actividad as(String alias) {
		return new Actividad(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Actividad rename(String name) {
		return new Actividad(name, null);
	}
}

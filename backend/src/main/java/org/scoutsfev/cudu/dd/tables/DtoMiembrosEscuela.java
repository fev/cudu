/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables;


import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.scoutsfev.cudu.dd.Public;
import org.scoutsfev.cudu.dd.tables.records.DtoMiembrosEscuelaRecord;


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
public class DtoMiembrosEscuela extends TableImpl<DtoMiembrosEscuelaRecord> {

	private static final long serialVersionUID = 456542202;

	/**
	 * The reference instance of <code>public.dto_miembros_escuela</code>
	 */
	public static final DtoMiembrosEscuela DTO_MIEMBROS_ESCUELA = new DtoMiembrosEscuela();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DtoMiembrosEscuelaRecord> getRecordType() {
		return DtoMiembrosEscuelaRecord.class;
	}

	/**
	 * The column <code>public.dto_miembros_escuela.id</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.cargo_id</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, Integer> CARGO_ID = createField("cargo_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.nombre_completo</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, String> NOMBRE_COMPLETO = createField("nombre_completo", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.nombre_grupo</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, String> NOMBRE_GRUPO = createField("nombre_grupo", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.etiqueta</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, String> ETIQUETA = createField("etiqueta", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.mesa_pedagogica</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, Boolean> MESA_PEDAGOGICA = createField("mesa_pedagogica", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.telefono</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, String> TELEFONO = createField("telefono", org.jooq.impl.SQLDataType.VARCHAR.length(15), this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.email</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.dto_miembros_escuela.fecha_nacimiento</code>.
	 */
	public final TableField<DtoMiembrosEscuelaRecord, Date> FECHA_NACIMIENTO = createField("fecha_nacimiento", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * Create a <code>public.dto_miembros_escuela</code> table reference
	 */
	public DtoMiembrosEscuela() {
		this("dto_miembros_escuela", null);
	}

	/**
	 * Create an aliased <code>public.dto_miembros_escuela</code> table reference
	 */
	public DtoMiembrosEscuela(String alias) {
		this(alias, DTO_MIEMBROS_ESCUELA);
	}

	private DtoMiembrosEscuela(String alias, Table<DtoMiembrosEscuelaRecord> aliased) {
		this(alias, aliased, null);
	}

	private DtoMiembrosEscuela(String alias, Table<DtoMiembrosEscuelaRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DtoMiembrosEscuela as(String alias) {
		return new DtoMiembrosEscuela(alias, this);
	}

	/**
	 * Rename this table
	 */
	public DtoMiembrosEscuela rename(String name) {
		return new DtoMiembrosEscuela(name, null);
	}
}

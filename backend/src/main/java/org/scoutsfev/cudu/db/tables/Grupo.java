/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.db.tables;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.scoutsfev.cudu.db.Keys;
import org.scoutsfev.cudu.db.Public;
import org.scoutsfev.cudu.db.tables.records.GrupoRecord;


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
public class Grupo extends TableImpl<GrupoRecord> {

	private static final long serialVersionUID = -917668728;

	/**
	 * The reference instance of <code>public.grupo</code>
	 */
	public static final Grupo GRUPO = new Grupo();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<GrupoRecord> getRecordType() {
		return GrupoRecord.class;
	}

	/**
	 * The column <code>public.grupo.id</code>.
	 */
	public final TableField<GrupoRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>public.grupo.nombre</code>.
	 */
	public final TableField<GrupoRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>public.grupo.codigo_postal</code>.
	 */
	public final TableField<GrupoRecord, Integer> CODIGO_POSTAL = createField("codigo_postal", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.grupo.municipio</code>.
	 */
	public final TableField<GrupoRecord, String> MUNICIPIO = createField("municipio", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>public.grupo.aniversario</code>.
	 */
	public final TableField<GrupoRecord, Date> ANIVERSARIO = createField("aniversario", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>public.grupo.telefono1</code>.
	 */
	public final TableField<GrupoRecord, String> TELEFONO1 = createField("telefono1", org.jooq.impl.SQLDataType.VARCHAR.length(15), this, "");

	/**
	 * The column <code>public.grupo.telefono2</code>.
	 */
	public final TableField<GrupoRecord, String> TELEFONO2 = createField("telefono2", org.jooq.impl.SQLDataType.VARCHAR.length(15), this, "");

	/**
	 * The column <code>public.grupo.email</code>.
	 */
	public final TableField<GrupoRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.grupo.web</code>.
	 */
	public final TableField<GrupoRecord, String> WEB = createField("web", org.jooq.impl.SQLDataType.VARCHAR.length(300), this, "");

	/**
	 * The column <code>public.grupo.entidad_patrocinadora</code>.
	 */
	public final TableField<GrupoRecord, String> ENTIDAD_PATROCINADORA = createField("entidad_patrocinadora", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.grupo.asociacion</code>.
	 */
	public final TableField<GrupoRecord, Integer> ASOCIACION = createField("asociacion", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.grupo.direccion</code>.
	 */
	public final TableField<GrupoRecord, String> DIRECCION = createField("direccion", org.jooq.impl.SQLDataType.VARCHAR.length(300).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>public.grupo</code> table reference
	 */
	public Grupo() {
		this("grupo", null);
	}

	/**
	 * Create an aliased <code>public.grupo</code> table reference
	 */
	public Grupo(String alias) {
		this(alias, GRUPO);
	}

	private Grupo(String alias, Table<GrupoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Grupo(String alias, Table<GrupoRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<GrupoRecord> getPrimaryKey() {
		return Keys.PK_GRUPO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<GrupoRecord>> getKeys() {
		return Arrays.<UniqueKey<GrupoRecord>>asList(Keys.PK_GRUPO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Grupo as(String alias) {
		return new Grupo(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Grupo rename(String name) {
		return new Grupo(name, null);
	}
}

/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.scoutsfev.cudu.dd.Sequences;
import org.scoutsfev.cudu.dd.tables.Actividad;
import org.scoutsfev.cudu.dd.tables.AsistenteActividad;
import org.scoutsfev.cudu.dd.tables.Asociado;
import org.scoutsfev.cudu.dd.tables.Cargo;
import org.scoutsfev.cudu.dd.tables.CargoAsociado;
import org.scoutsfev.cudu.dd.tables.Curso;
import org.scoutsfev.cudu.dd.tables.CursoFormador;
import org.scoutsfev.cudu.dd.tables.CursoParticipante;
import org.scoutsfev.cudu.dd.tables.DtoActividadDetalle;
import org.scoutsfev.cudu.dd.tables.DtoCargoAsociado;
import org.scoutsfev.cudu.dd.tables.DtoDatosGrupoRama;
import org.scoutsfev.cudu.dd.tables.DtoDatosGrupoTipo;
import org.scoutsfev.cudu.dd.tables.DtoDatosPeriodoTipo;
import org.scoutsfev.cudu.dd.tables.DtoDatosRamaTipo;
import org.scoutsfev.cudu.dd.tables.DtoEstadoInscripcion;
import org.scoutsfev.cudu.dd.tables.DtoMiembrosCurso;
import org.scoutsfev.cudu.dd.tables.DtoMiembrosEscuela;
import org.scoutsfev.cudu.dd.tables.Ficha;
import org.scoutsfev.cudu.dd.tables.Grupo;
import org.scoutsfev.cudu.dd.tables.Impresion;
import org.scoutsfev.cudu.dd.tables.InformacionPago;
import org.scoutsfev.cudu.dd.tables.Liquidacion;
import org.scoutsfev.cudu.dd.tables.LiquidacionAsociado;
import org.scoutsfev.cudu.dd.tables.LiquidacionBalance;
import org.scoutsfev.cudu.dd.tables.LiquidacionBalanceResumen;
import org.scoutsfev.cudu.dd.tables.LiquidacionCalculo;
import org.scoutsfev.cudu.dd.tables.LiquidacionGrupos;
import org.scoutsfev.cudu.dd.tables.Ronda;
import org.scoutsfev.cudu.dd.tables.SchemaVersion;
import org.scoutsfev.cudu.dd.tables.Token;
import org.scoutsfev.cudu.dd.tables.ValoresFederativos;
import org.scoutsfev.cudu.dd.tables.ValoresPorAsociacion;
import org.scoutsfev.cudu.dd.tables.ValoresPorLiquidacion;


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
public class Public extends SchemaImpl {

	private static final long serialVersionUID = 1307808116;

	/**
	 * The reference instance of <code>public</code>
	 */
	public static final Public PUBLIC = new Public();

	/**
	 * No further instances allowed
	 */
	private Public() {
		super("public");
	}

	@Override
	public final List<Sequence<?>> getSequences() {
		List result = new ArrayList();
		result.addAll(getSequences0());
		return result;
	}

	private final List<Sequence<?>> getSequences0() {
		return Arrays.<Sequence<?>>asList(
			Sequences.ACTIVIDAD_ID_SEQ,
			Sequences.ASOCIADO_ID_SEQ,
			Sequences.CARGO_ID_SEQ,
			Sequences.CURSO_ID_SEQ,
			Sequences.CURSO_PARTICIPANTE_SECUENCIA_INSCRIPCION_SEQ,
			Sequences.FICHA_ID_SEQ,
			Sequences.LIQUIDACION_ID_SEQ);
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			Actividad.ACTIVIDAD,
			AsistenteActividad.ASISTENTE_ACTIVIDAD,
			Asociado.ASOCIADO,
			Cargo.CARGO,
			CargoAsociado.CARGO_ASOCIADO,
			Curso.CURSO,
			CursoFormador.CURSO_FORMADOR,
			CursoParticipante.CURSO_PARTICIPANTE,
			DtoActividadDetalle.DTO_ACTIVIDAD_DETALLE,
			DtoCargoAsociado.DTO_CARGO_ASOCIADO,
			DtoDatosGrupoRama.DTO_DATOS_GRUPO_RAMA,
			DtoDatosGrupoTipo.DTO_DATOS_GRUPO_TIPO,
			DtoDatosPeriodoTipo.DTO_DATOS_PERIODO_TIPO,
			DtoDatosRamaTipo.DTO_DATOS_RAMA_TIPO,
			DtoEstadoInscripcion.DTO_ESTADO_INSCRIPCION,
			DtoMiembrosCurso.DTO_MIEMBROS_CURSO,
			DtoMiembrosEscuela.DTO_MIEMBROS_ESCUELA,
			Ficha.FICHA,
			Grupo.GRUPO,
			Impresion.IMPRESION,
			InformacionPago.INFORMACION_PAGO,
			Liquidacion.LIQUIDACION,
			LiquidacionAsociado.LIQUIDACION_ASOCIADO,
			LiquidacionBalance.LIQUIDACION_BALANCE,
			LiquidacionBalanceResumen.LIQUIDACION_BALANCE_RESUMEN,
			LiquidacionCalculo.LIQUIDACION_CALCULO,
			LiquidacionGrupos.LIQUIDACION_GRUPOS,
			Ronda.RONDA,
			SchemaVersion.SCHEMA_VERSION,
			Token.TOKEN,
			ValoresFederativos.VALORES_FEDERATIVOS,
			ValoresPorAsociacion.VALORES_POR_ASOCIACION,
			ValoresPorLiquidacion.VALORES_POR_LIQUIDACION);
	}
}
